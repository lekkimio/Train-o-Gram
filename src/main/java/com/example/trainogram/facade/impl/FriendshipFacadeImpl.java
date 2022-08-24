package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import com.example.trainogram.service.FriendshipService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipFacadeImpl implements FriendshipFacade {

    private final FriendshipService friendshipService;
    private final NotificationService notificationService;
    private final UserService userService;

    public FriendshipFacadeImpl(FriendshipService friendshipService, NotificationService notificationService, UserService userService) {
        this.friendshipService = friendshipService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @Override
    public List<User> findAllFriends(Long userId) {
        return friendshipService.findAllFriends(userId);
    }

    @Override
    public List<User> findAllRequests(Long userId) {
        return friendshipService.findAllRequests(userId);
    }

    @Override
    public Friendship addFriend(Long friendId) throws UserNotFoundException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);
        notificationService.sendNotification(owner, "You have a new friend request");
        return friendshipService.addFriend(owner,friend);
    }

    @Override
    public void deleteFriend(Long friendId) throws UserNotFoundException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);
        friendshipService.deleteFriend(owner,friend);
    }
}
