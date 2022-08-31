package com.example.trainogram.facade;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.FriendshipDto;
import com.example.trainogram.model.dto.UserDto;

import java.util.List;

public interface FriendshipFacade {
    List<UserDto> findAllFriends(Long userId);

    List<UserDto> findAllRequests(Long userId);

    FriendshipDto addFriend(Long friendId) throws UserNotFoundException;

    void deleteFriend(Long friendId) throws UserNotFoundException;
}
