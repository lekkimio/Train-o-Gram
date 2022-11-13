package com.example.trainogram.controller;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final ModelMapper modelMapper;

    public SubscriptionController(SubscriptionService subscriptionService, ModelMapper modelMapper) {
        this.subscriptionService = subscriptionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{userId}")
    public List<UserResponseDto> getAllSubscribers(@PathVariable Long userId) {
        List<User> users = subscriptionService.findAllSubscribers(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);
    }

    @GetMapping("/request/{userId}")
    public List<UserResponseDto> getAllRequests(@PathVariable Long userId) {

        List<User> users =  subscriptionService.findAllRequests(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);

    }

    @PostMapping("/{friendId}")
    public void createSubscription(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long friendId) throws Status434UserNotFound, Status441FriendshipNotFound, Status449FriendshipIllegalRequest {
        subscriptionService.createSubscription(token, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long friendId) throws Status434UserNotFound, Status441FriendshipNotFound {
        subscriptionService.deleteSubscribtion(token, friendId);
    }







}
