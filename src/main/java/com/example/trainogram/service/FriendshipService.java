package com.example.trainogram.service;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;

import java.util.List;

public interface FriendshipService {

    Friendship addFriend(Friendship friendship, boolean friendshipExistence);

    void deleteFriend(User user, User friend) throws CustomException;

    List<User> findAllRequests(Long userId);

    List<User> findAllFriends(Long userId);

    Friendship findByOwnerAndFriend(User owner, User friend);
}
