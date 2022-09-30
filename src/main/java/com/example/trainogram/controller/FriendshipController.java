package com.example.trainogram.controller;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.FriendshipFacade;
import com.example.trainogram.model.dto.response.UserResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/friendship")
public class FriendshipController {

    private final FriendshipFacade friendshipFacade;

    public FriendshipController(FriendshipFacade friendshipFacade) {
        this.friendshipFacade = friendshipFacade;
    }

    @GetMapping("/{userId}")
    public List<UserResponseDto> getAllFriends(@PathVariable Long userId) {
        return friendshipFacade.getAllFriends(userId);
    }

    @GetMapping("/pending/{userId}")
    public List<UserResponseDto> getAllRequests(@PathVariable Long userId) {
        return friendshipFacade.getAllRequests(userId);
    }

    @PostMapping("/{friendId}")
    public void addFriend(@PathVariable Long friendId) throws CustomException {
        friendshipFacade.addFriend(friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@PathVariable Long friendId) throws CustomException {
        friendshipFacade.deleteFriend(friendId);
    }







}
