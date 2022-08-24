package com.example.trainogram.facade;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;

import java.util.List;

public interface FriendshipFacade {
    List<User> findAllFriends(Long userId);

    List<User> findAllRequests(Long userId);

    Friendship addFriend(Long friendId) throws UserNotFoundException;

    void deleteFriend(Long friendId) throws UserNotFoundException;
}
