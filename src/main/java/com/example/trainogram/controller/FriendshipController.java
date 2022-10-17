package com.example.trainogram.controller;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.FriendshipService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final ModelMapper modelMapper;

    public FriendshipController(FriendshipService friendshipService, ModelMapper modelMapper) {
        this.friendshipService = friendshipService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{userId}")
    public List<UserResponseDto> getAllFriends(@PathVariable Long userId) {
        List<User> users = friendshipService.findAllFriends(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);
    }

    @GetMapping("/pending/{userId}")
    public List<UserResponseDto> getAllRequests(@PathVariable Long userId) {

        List<User> users =  friendshipService.findAllRequests(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);

    }

    @PostMapping("/{friendId}")
    public void addFriend(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long friendId) throws Status434UserNotFound, Status441FriendshipNotFound, Status449FriendshipIllegalRequest {
        friendshipService.createFriendship(token, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long friendId) throws Status434UserNotFound, Status441FriendshipNotFound {
        friendshipService.deleteFriend(token, friendId);
    }







}
