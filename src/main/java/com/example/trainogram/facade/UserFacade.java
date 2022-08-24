package com.example.trainogram.facade;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;

import java.util.List;

public interface UserFacade {


    void deleteUser(Long id);

    User updateUser(Long id, User user, String key) throws UserNotFoundException;

    User addUser(User user, String key) throws UserNotFoundException;

    User findUserById(Long id) throws UserNotFoundException;

    List<User> findAllUsers() throws UserNotFoundException;
}
