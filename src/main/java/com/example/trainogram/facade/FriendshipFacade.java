package com.example.trainogram.facade;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.model.dto.FriendshipDto;
import com.example.trainogram.model.dto.UserDto;

import java.util.List;

public interface FriendshipFacade {
    List<UserDto> findAllFriends(Long userId);

    List<UserDto> findAllRequests(Long userId);

    FriendshipDto addFriend(Long friendId) throws UserException;

    void deleteFriend(Long friendId) throws UserException;
}
