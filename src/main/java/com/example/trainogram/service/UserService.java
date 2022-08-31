package com.example.trainogram.service;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    User addUser(User user) throws UserNotFoundException;

    void deleteUser(Long id);

    User updateUser(Long id, User user) throws UserNotFoundException;

    List<User> findAllUsers();

    User findUserById(Long id) throws UserNotFoundException;

    User findAuthenticatedUser();
}
