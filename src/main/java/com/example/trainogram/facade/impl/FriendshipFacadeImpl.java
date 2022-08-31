package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.FriendshipDto;
import com.example.trainogram.model.dto.UserDto;
import com.example.trainogram.service.FriendshipService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@Slf4j
public class FriendshipFacadeImpl implements FriendshipFacade {

    private final FriendshipService friendshipService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final ModelMapper mapToDto;

    public FriendshipFacadeImpl(FriendshipService friendshipService, NotificationService notificationService, UserService userService, ModelMapper mapToDto) {
        this.friendshipService = friendshipService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.mapToDto = mapToDto;
    }

    @Override
    public List<UserDto> findAllFriends(Long userId) {
        List<User> users = friendshipService.findAllFriends(userId);
        Type listType = new TypeToken<List<UserDto>>(){}.getType();
        return mapToDto.map(users,listType);
    }

    @Override
    public List<UserDto> findAllRequests(Long userId) {
        List<User> users = friendshipService.findAllRequests(userId);
        Type listType = new TypeToken<List<UserDto>>(){}.getType();
        return mapToDto.map(users,listType);
    }

    @Override
    public FriendshipDto addFriend(Long friendId) throws UserNotFoundException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);

        Friendship friendship = friendshipService.addFriend(owner, friend);

//        notificationService.sendNotification(owner, "You have a new friend request");

        return mapToDto.map(friendship, FriendshipDto.class);
    }

    @Override
    public void deleteFriend(Long friendId) throws UserNotFoundException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);
        friendshipService.deleteFriend(owner,friend);
    }
}
