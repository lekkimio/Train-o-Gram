package com.example.trainogram.controller;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipFacade friendshipFacade;

    public FriendshipController(FriendshipFacade friendshipFacade) {
        this.friendshipFacade = friendshipFacade;
    }

    @GetMapping("/{userId}")
    public List<User> getAllFriends(@PathVariable Long userId) {
        return friendshipFacade.findAllFriends(userId);
    }

    @GetMapping("/pending/{userId}")
    public List<User> getAllRequests(@PathVariable Long userId) {
        return friendshipFacade.findAllRequests(userId);
    }

    @PostMapping("/{friendId}")
    public Friendship addFriend(@PathVariable Long friendId) throws UserNotFoundException {
       return friendshipFacade.addFriend(friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@PathVariable Long friendId) throws UserNotFoundException {
        friendshipFacade.deleteFriend(friendId);
    }







}
