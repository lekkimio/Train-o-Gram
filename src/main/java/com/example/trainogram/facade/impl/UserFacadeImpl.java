package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.service.PictureService;
import com.example.trainogram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final PictureService pictureService;

    @Value("${admin.key}")
    private String adminKey;

    public UserFacadeImpl(UserService userService, PictureService pictureService) {
        this.userService = userService;
        this.pictureService = pictureService;
    }


    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @Override
    public List<User> findAllUsers() throws UserNotFoundException {
        User user = userService.findAuthenticatedUser();
        if (user.getRole().equals(Role.USER)) {
            return Collections.singletonList(userService.findUserById(user.getId()));
        } else {
            return userService.findAllUsers();
        }
    }

    @Override
    public User addUser(User user, String key) throws UserNotFoundException {
        if (key.equals(adminKey)){
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return userService.addUser(user);
    }

    @Override
    public User updateUser(Long id, User user, String key) throws UserNotFoundException {
        if (key.equals(adminKey)){
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return userService.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
