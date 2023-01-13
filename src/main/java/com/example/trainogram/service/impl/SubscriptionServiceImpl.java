package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.Follow;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FollowRepository;
import com.example.trainogram.service.SubscriptionService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {


    //TODO: FriendshipServiceImpl

    private final FollowRepository followRepository;
    private final UserService userService;
    private final NotificationService notificationService;


    @Override
    public void createSubscription(String token, Long friendId)
            throws Status434UserNotFound, Status449FriendshipIllegalRequest{
        String link = "http://localhost:8080/users/";

        User owner = userService.findAuthenticatedUser(token);
        User friend = userService.findById(friendId);
        if (!owner.getId().equals(friendId)) {
            if (followRepository.findByOwnerAndFriend(owner, friend).isEmpty()) {
                Optional<Follow> otherSideFollow = followRepository.findByOwnerAndFriend(friend, owner);
                if (otherSideFollow.isPresent()) {
                    followRepository.save(Follow.builder()
                            .owner(owner)
                            .friend(friend)
                            .build());
                    followRepository.save(otherSideFollow.get());
                    notificationService.sendNotification(friend, "Your friend request to " + owner.getUsername() + " have been approved", link + friendId);
                } else {
                    followRepository.save(Follow.builder()
                            .friend(friend)
                            .owner(owner)
                            .build());
//                    if(){
                    notificationService.sendNotification(friend, "You have new friend request from " + owner.getUsername(), link + friendId);
                }
            } else {
                throw new Status449FriendshipIllegalRequest(owner,friend);
            }
        } else {
            throw new Status449FriendshipIllegalRequest();
        }

    }

    @Override
    public void unFollow(String token, Long id) throws Status434UserNotFound, Status441FriendshipNotFound {
        User owner = userService.findAuthenticatedUser(token);
        User friend = userService.findById(id);
        Follow follow = findByOwnerAndFriend(owner, friend);
        if (follow != null){
            followRepository.delete(follow);
            followRepository.findByOwnerAndFriend(friend, owner).ifPresent(followRepository::save);
        }
    }

    @Override
    public List<User> findAllFollowers(Long userId) {
        return followRepository.findAllFollowersByOwnerId(userId);
    }

    @Override
    public List<User> findAllFollowing(Long userId) {
        return followRepository.findAllFollowingByOwnerId(userId);
    }

    @Override
    public Follow findByOwnerAndFriend(User owner, User friend) throws Status441FriendshipNotFound {
        return followRepository.findByOwnerAndFriend(owner,friend).orElseThrow(()-> new Status441FriendshipNotFound(owner, friend));
    }
}
