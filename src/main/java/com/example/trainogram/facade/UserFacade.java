package com.example.trainogram.facade;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserFacade {


    void deleteUser(Long id);

    UserDto updateUser(Long id, User user, MultipartFile file, String key) throws UserException;

    UserDto addUser(User user, String key) throws UserException, IOException;

    UserDto findUserById(Long id) throws UserException;

    List<UserDto> findAllUsers() throws UserException;

    byte[] getAvatar(Long id) throws UserException, IOException;
}
