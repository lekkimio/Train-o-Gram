package com.example.trainogram.controller;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import com.example.trainogram.model.dto.response.NotificationResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping()
    public List<UserResponseDto> getAllUsers() throws  CustomException {
        return userFacade.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) throws  CustomException {
        return userFacade.getUserById(id);
    }

    @PostMapping()
    public void createUser(UserAuthDto user/*, Multi partFile file*/) throws  IOException, CustomException {
        userFacade.createUser(user/* file*/);
    }

    @PutMapping()
    public void updateUser(UserRequestDto user, MultipartFile file) throws CustomException {
        userFacade.updateUser(user, file);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }


    @ResponseBody
    @GetMapping(value = "/avatar/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatar(@PathVariable Long id) throws  IOException, CustomException {
        /*String path = "/static/1/2.jpg";
        InputStream in = this.getClass().getResourceAsStream(path);
        assert in != null;
        byte[] result = null;
        try {
            result = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;*/
        return  userFacade.getAvatar(id);
    }

    @GetMapping("/notification")
    public List<NotificationResponseDto> getAllNotification() throws CustomException {
        return userFacade.getAllNotification();
    }

    @GetMapping("/notification/{notificationId}")
    public NotificationResponseDto getNotification(@PathVariable Long notificationId) throws CustomException {
        return userFacade.getNotificationById(notificationId);
    }
}
