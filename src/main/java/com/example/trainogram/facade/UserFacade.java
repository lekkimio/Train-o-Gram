package com.example.trainogram.facade;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import com.example.trainogram.model.dto.response.NotificationResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserFacade {


    void deleteUser(Long id);

    void updateUser(UserRequestDto user, MultipartFile file) throws CustomException;

    void createUser(UserAuthDto user) throws IOException, CustomException;

    UserResponseDto getUserById(Long id) throws  CustomException;

    List<UserResponseDto> getAllUsers() throws  CustomException;

    byte[] getAvatar(Long id) throws  IOException, CustomException;

    List<NotificationResponseDto> getAllNotification() throws CustomException;

    NotificationResponseDto getNotificationById(Long notificationId) throws CustomException;
}
