package com.example.trainogram.controller;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.dto.FriendshipDto;
import com.example.trainogram.model.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/friendship")
public class FriendshipController {

    private final FriendshipFacade friendshipFacade;

    public FriendshipController(FriendshipFacade friendshipFacade) {
        this.friendshipFacade = friendshipFacade;
    }


    @GetMapping
    public String getFriendsHome(){
        return "sdsdasdsadsda";
    }

    @GetMapping("/{userId}")
    public List<UserDto> getAllFriends(@PathVariable Long userId) {
        return friendshipFacade.findAllFriends(userId);
    }

    @GetMapping("/pending/{userId}")
    public List<UserDto> getAllRequests(@PathVariable Long userId) {
        return friendshipFacade.findAllRequests(userId);
    }

    @PostMapping("/{friendId}")
    public FriendshipDto addFriend(@PathVariable Long friendId) throws UserException {
        return friendshipFacade.addFriend(friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@PathVariable Long friendId) throws UserException {
        friendshipFacade.deleteFriend(friendId);
    }







}
