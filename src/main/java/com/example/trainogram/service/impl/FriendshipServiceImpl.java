package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.FriendshipStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FriendshipRepository;
import com.example.trainogram.service.FriendshipService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {


    //TODO: FriendshipServiceImpl

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final NotificationService notificationService;


    @Override
    public void createFriendship(String token, Long friendId)
            throws Status434UserNotFound, Status441FriendshipNotFound, Status449FriendshipIllegalRequest{
        /*friendshipRepository.save(Friendship.builder()
                .owner(userService.findAuthenticatedUser())
                .friend(userService.findById(id))
                .status(RequestStatus.REQUEST.name())
                .build());*/

        User owner = userService.findAuthenticatedUser(token);
        User friend = userService.findById(friendId);
        if (!owner.getId().equals(friendId)) {
            if (friendshipRepository.findByOwnerAndFriend(owner, friend).isEmpty()) {
                Friendship otherSideFriendship = findByOwnerAndFriend(friend, owner);
                if (otherSideFriendship != null) {
                    friendshipRepository.save(Friendship.builder()
                            .owner(owner)
                            .friend(friend)
                            .status(FriendshipStatus.FRIEND.name())
                            .build());
                             otherSideFriendship.setStatus(FriendshipStatus.FRIEND.name());
                    friendshipRepository.save(otherSideFriendship);
                    notificationService.sendNotification(friend, "Your friend request to " + owner.getUsername() + " have been approved");
                } else {
                    friendshipRepository.save(Friendship.builder().friend(friend).owner(owner).status(FriendshipStatus.REQUEST.name()).build());
//                    if(){
                    notificationService.sendNotification(friend, "You have new friend request from " + owner.getUsername());
                }
            } else {
                throw new Status449FriendshipIllegalRequest(owner,friend);
            }
        } else {
            throw new Status449FriendshipIllegalRequest();
        }

    }

    @Override
    public void deleteFriend(String token, Long id) throws Status434UserNotFound, Status441FriendshipNotFound {
        User owner = userService.findAuthenticatedUser(token);
        User friend = userService.findById(id);
        Friendship friendship = findByOwnerAndFriend(owner, friend);
        if (friendship != null){
            friendshipRepository.delete(friendship);
            Friendship optionalFriendship = friendshipRepository.findByOwnerAndFriend(friend, owner).orElse(null);
            if (optionalFriendship!=null){
                optionalFriendship.setStatus(FriendshipStatus.REQUEST.name());
                friendshipRepository.save(optionalFriendship);
            }
        }
    }

    @Override
    public List<User> findAllRequests(Long userId) {
        return friendshipRepository.findAllRequestByOwnerId(userId);
    }

    @Override
    public List<User> findAllFriends(Long userId) {
        return friendshipRepository.findAllFriendsByOwnerId(userId);
    }

    @Override
    public Friendship findByOwnerAndFriend(User owner, User friend) throws Status441FriendshipNotFound {
        return friendshipRepository.findByOwnerAndFriend(owner,friend).orElseThrow(()-> new Status441FriendshipNotFound(owner, friend));
    }
}
