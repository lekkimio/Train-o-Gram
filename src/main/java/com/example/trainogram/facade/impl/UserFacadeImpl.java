package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
import com.example.trainogram.service.PictureService;
import com.example.trainogram.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final PictureService pictureService;

    private ModelMapper mapToDto;

    @Value("${admin.key}")
    private String adminKey;

    public UserFacadeImpl(UserService userService, PictureService pictureService, ModelMapper mapToDto) {
        this.userService = userService;
        this.pictureService = pictureService;
        this.mapToDto = mapToDto;
    }


    @Override
    public UserDto findUserById(Long id) throws UserNotFoundException {
        User user = userService.findUserById(id);
        return mapToDto.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() throws UserNotFoundException {
        User user = userService.findAuthenticatedUser();
        if (user.getRole().equals(Role.USER)) {
            return Collections.singletonList(mapToDto.map(userService.findUserById(user.getId()), UserDto.class));
        } else {
            List<User> users = userService.findAllUsers();
            Type listType = new TypeToken<List<UserDto>>(){}.getType();
            return mapToDto.map(users,listType);

        }
    }

    @Override
    public UserDto addUser(User user, MultipartFile file, String key) throws UserNotFoundException, IOException {
        if (Objects.equals(key, adminKey)){
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        if (file != null){
            user.setAvatar(pictureService.addPicture(file));
        }
        User savedUser = userService.addUser(user);
        return mapToDto.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, User user, String key) throws UserNotFoundException {
        if (key.equals(adminKey)){
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return mapToDto.map(userService.updateUser(id, user), UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
