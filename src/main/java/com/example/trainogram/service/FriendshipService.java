package com.example.trainogram.service;

import com.example.trainogram.model.User;

public interface FriendshipService {

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
