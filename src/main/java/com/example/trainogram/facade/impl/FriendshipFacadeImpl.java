package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.RequestStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.FriendshipService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
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
    public List<UserResponseDto> getAllFriends(Long userId) {
        List<User> users = friendshipService.findAllFriends(userId);
        Type listType = new TypeToken<List<UserResponseDto>>() {
        }.getType();
        return mapToDto.map(users, listType);
    }

    @Override
    public List<UserResponseDto> getAllRequests(Long userId) {
        List<User> users = friendshipService.findAllRequests(userId);
        Type listType = new TypeToken<List<UserResponseDto>>() {
        }.getType();
        return mapToDto.map(users, listType);
    }

    @Override
    public void addFriend(Long friendId) throws  CustomException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);
        if (!owner.getId().equals(friendId)) {
            Friendship friendship = friendshipService.findByOwnerAndFriend(owner, friend);

            if (friendship == null) {
                Friendship otherSideFriendship = friendshipService.findByOwnerAndFriend(friend, owner);
                if (otherSideFriendship != null) {
                    friendshipService.addFriend(otherSideFriendship, true);
                    notificationService.sendNotification(friend, "Your friend request to " + owner.getUsername() + " have been approved");
                } else
                    friendshipService.addFriend(Friendship.builder().friend(friend).owner(owner).status(RequestStatus.REQUEST.name()).build(), false);
                    notificationService.sendNotification(friend, "You have new friend request from " + owner.getUsername());
            } else {
                throw new CustomException("Request already sent", HttpStatus.CREATED);
            }
        } else {
            throw new CustomException("User cant add himself to friend", HttpStatus.BAD_REQUEST);
        }
    }



    @Override
    public void deleteFriend(Long friendId) throws  CustomException {
        User owner = userService.findAuthenticatedUser();
        User friend = userService.findUserById(friendId);
        friendshipService.deleteFriend(owner, friend);
    }
}


    /* @Override
     public void addFriend(Long friendId) throws UserException, FriendshipException {
         User owner = userService.findAuthenticatedUser();//6
         User friend = userService.findUserById(friendId);//5

         if (!Objects.equals(owner.getId(), friendId)) {
             Friendship friendship = friendshipService.findByUserAndFriend(friend, owner);
             boolean friendshipExistence = friendship != null;

             if (friendshipExistence) {
                 notificationService.sendNotification(friend, "Your friend request have been approved");
             }
             else {
                 notificationService.sendNotification(friend, "You have a new friend request");
             }
             if (friendshipService.findByUserAndFriend(owner,friend) == null)
                 friendshipService.addFriend(friendship, friendshipExistence)

             if (friendshipService.findByOwnerAndFriend(owner,friend) == null) {
                 if (friendshipService.findByOwnerAndFriend(friend 5, owner 6) == null) {
                     friendshipService.addFriend(Friendship.builder()
                             .friend(friend).owner(owner).build(), false);
                     notificationService.sendNotification(friend, "You have new friend request");
                 }else {
 //                    throw new FriendshipException("Friendship already exists");
                     friendshipService.addFriend(Friendship.builder().id(friendshipService.findByOwnerAndFriend(friend,owner).getId())
                             .friend(friend).owner(owner).build(), true);
                     notificationService.sendNotification(friend, "Your request have been approved");

                 }

             }else {
                 throw new FriendshipException("Request already sent");
             }


             }
     }*/
