package com.example.trainogram.controller;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status436UserExistsException;
import com.example.trainogram.exception.Status440NotificationNotFound;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import com.example.trainogram.model.dto.response.NotificationResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<UserResponseDto> getAllUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws Status434UserNotFound, Status435NoAuthorities {
        List<User> users = userService.findAllUsers(token);
        Type listType = new TypeToken<List<UserResponseDto>>() {}.getType();
        return modelMapper.map(users, listType);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) throws Status434UserNotFound, Status435NoAuthorities {
        return modelMapper.map(userService.findUserById(token, id), UserResponseDto.class);
    }

    @PostMapping()
    public void createUser(UserAuthDto user) throws Status436UserExistsException {
        userService.createUser(user);
    }

    @PutMapping()
    public void updateUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                           UserRequestDto user, MultipartFile file) throws IOException, Status435NoAuthorities, Status434UserNotFound {
        userService.updateUser(token,user, file);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,@PathVariable Long id) throws IOException, Status434UserNotFound, Status435NoAuthorities {
        userService.deleteUser(token, id);
    }

    /*    @ResponseBody
    @GetMapping(value = "/avatar/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatar(@PathVariable Long id) throws  IOException, CustomException {
        String path = "/static/1/2.jpg";
        InputStream in = this.getClass().getResourceAsStream(path);
        assert in != null;
        byte[] result = null;
        try {
            result = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
        return  userService.getAvatar(id);
    }*/

    @GetMapping("/notification")
    public List<NotificationResponseDto> getAllNotification(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        List<Notification> users = userService.getAllNotification(token);
        Type listType = new TypeToken<List<NotificationResponseDto>>() {}.getType();
        return modelMapper.map(users, listType);
    }

    @GetMapping("/notification/{notificationId}")
    public NotificationResponseDto getNotification(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable Long notificationId) throws Status435NoAuthorities, Status440NotificationNotFound {
        return modelMapper.map(userService.getNotificationById(token,notificationId), NotificationResponseDto.class);
    }
}
