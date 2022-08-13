package com.example.trainogram.facade.impl;

import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.service.FriendshipService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Component;

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
}
