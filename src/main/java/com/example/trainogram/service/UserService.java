package com.example.trainogram.service;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    User createUser(User user) throws  CustomException;

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> findAllUsers();

    User findUserById(Long id) throws CustomException;

    User findAuthenticatedUser();
}
