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
@RequestMapping("/users")
public class FollowController {

    private final SubscriptionService subscriptionService;
    private final ModelMapper modelMapper;

    public FollowController(SubscriptionService subscriptionService, ModelMapper modelMapper) {
        this.subscriptionService = subscriptionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/following/{userId}")
    public List<UserResponseDto> getAllFollowing(@PathVariable Long userId) {
        List<User> users = subscriptionService.findAllFollowing(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);
    }

    @GetMapping("/followers/{userId}")
    public List<UserResponseDto> getAllFollowers(@PathVariable Long userId) {

        List<User> users =  subscriptionService.findAllFollowers(userId);
        Type list = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,list);

    }

    @PostMapping("/follow/{userId}")
    public void createSubscription(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long userId) throws Status434UserNotFound, Status441FriendshipNotFound, Status449FriendshipIllegalRequest {
        subscriptionService.createSubscription(token, userId);
    }

    @DeleteMapping("/unfollow/{userId}")
    public void deleteFollow(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long userId) throws Status434UserNotFound, Status441FriendshipNotFound {
        subscriptionService.unFollow(token, userId);
    }







}
