package com.example.trainogram.service;

import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;

import java.util.List;

public interface FriendshipService {

    Friendship addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> findAllRequests(Long userId);

    List<User> findAllFriends(Long userId);
}
