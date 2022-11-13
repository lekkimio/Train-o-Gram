package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.Subscription;
import com.example.trainogram.model.SubscriptionStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FriendshipRepository;
import com.example.trainogram.service.SubscriptionService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {


    //TODO: FriendshipServiceImpl

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final NotificationService notificationService;


    @Override
    public void createSubscription(String token, Long friendId)
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
                Subscription otherSideSubscription = findByOwnerAndFriend(friend, owner);
                if (otherSideSubscription != null) {
                    friendshipRepository.save(Subscription.builder()
                            .owner(owner)
                            .friend(friend)
                            .status(SubscriptionStatus.MUTUAL.name())
                            .build());
                             otherSideSubscription.setStatus(SubscriptionStatus.MUTUAL.name());
                    friendshipRepository.save(otherSideSubscription);
                    notificationService.sendNotification(friend, "Your friend request to " + owner.getUsername() + " have been approved", "");
                } else {
                    friendshipRepository.save(Subscription.builder().friend(friend).owner(owner).status(SubscriptionStatus.REQUEST.name()).build());
//                    if(){
                    notificationService.sendNotification(friend, "You have new friend request from " + owner.getUsername(), "");
                }
            } else {
                throw new Status449FriendshipIllegalRequest(owner,friend);
            }
        } else {
            throw new Status449FriendshipIllegalRequest();
        }

    }

    @Override
    public void deleteSubscribtion(String token, Long id) throws Status434UserNotFound, Status441FriendshipNotFound {
        User owner = userService.findAuthenticatedUser(token);
        User friend = userService.findById(id);
        Subscription subscription = findByOwnerAndFriend(owner, friend);
        if (subscription != null){
            friendshipRepository.delete(subscription);
            Subscription optionalSubscription = friendshipRepository.findByOwnerAndFriend(friend, owner).orElse(null);
            if (optionalSubscription !=null){
                optionalSubscription.setStatus(SubscriptionStatus.REQUEST.name());
                friendshipRepository.save(optionalSubscription);
            }
        }
    }

    @Override
    public List<User> findAllRequests(Long userId) {
        return friendshipRepository.findAllRequestByOwnerId(userId);
    }

    @Override
    public List<User> findAllSubscribers(Long userId) {
        return friendshipRepository.findAllFriendsByOwnerId(userId);
    }

    @Override
    public Subscription findByOwnerAndFriend(User owner, User friend) throws Status441FriendshipNotFound {
        return friendshipRepository.findByOwnerAndFriend(owner,friend).orElseThrow(()-> new Status441FriendshipNotFound(owner, friend));
    }
}
