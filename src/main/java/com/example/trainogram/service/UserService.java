package com.example.trainogram.service;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.model.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    User addUser(User user) throws UserException;

    void deleteUser(Long id);

    User updateUser(User user) throws UserException;

    List<User> findAllUsers();

    User findUserById(Long id) throws UserException;

    User findAuthenticatedUser();
}
