package com.example.trainogram.facade;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserFacade {


    void deleteUser(Long id);

    UserDto updateUser(Long id, User user, String key) throws UserNotFoundException;

    UserDto addUser(User user, MultipartFile file, String key) throws UserNotFoundException, IOException;

    UserDto findUserById(Long id) throws UserNotFoundException;

    List<UserDto> findAllUsers() throws UserNotFoundException;
}
