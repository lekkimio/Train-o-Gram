package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import com.example.trainogram.model.dto.response.NotificationResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class UserFacadeImpl implements UserFacade {


    private final UserService userService;
    private final NotificationService notificationService;

    private final ModelMapper mapToDto;


//    private final String folder = "/static/";

    public UserFacadeImpl(UserService userService, NotificationService notificationService, ModelMapper mapToDto) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.mapToDto = mapToDto;
    }


    @Override
    public UserResponseDto getUserById(Long id) throws  CustomException {
        User user = userService.findUserById(id);
        return mapToDto.map(user,UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() throws  CustomException {
        User user = userService.findAuthenticatedUser();
        if (user.getRole().equals(Role.USER)) {
            return Collections.singletonList(mapToDto.map(userService.findUserById(user.getId()), UserResponseDto.class));
        } else {
            List<User> users = userService.findAllUsers();
            Type listType = new TypeToken<List<UserResponseDto>>(){}.getType();
            return mapToDto.map(users,listType);

        }
    }



    @Override
    public void createUser(UserAuthDto user) throws  CustomException {
//        if (Objects.equals(key, adminKey)){
//            user.setRole(Role.ADMIN);
//        } else {
//            user.setRole(Role.USER);
//        }
//        if (file != null){
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(folder+user.getId().toString()+ File.separator+file.getOriginalFilename());
//            Files.write(path,bytes);
//
//            user.setAvatar(file.getOriginalFilename());
//        }
        userService.createUser(
                User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .avatar("user_picture.jpg")
                        .role(Role.USER).build());
    }

    @Override
    public void updateUser(UserRequestDto dto, MultipartFile file) throws CustomException {
//        User userToUpdate = userService.findUserById(id);
////        if (key!=null && key.equals(adminKey)){
////            userToUpdate.setRole(Role.ADMIN);
////        }
//        if (file != null) {
//            try {
//                saveImage(file,id);
//                userToUpdate.setAvatar(file.getOriginalFilename());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        userService.updateUser(userToUpdate);

        User user = userService.findUserByUsername(dto.getUsername());
        if (/*userService.findUserById(user.getId()) != null*/ user != null) {
            if (userService.findAuthenticatedUser().getId().equals(user.getId())) {
                try {
                    User userToUpdate = userService.findUserById(user.getId());
                    if (user.getUsername() != null){
                        userToUpdate.setUsername(user.getUsername());
                    }
                    if (user.getPassword() != null){
                        userToUpdate.setPassword(user.getPassword());
                    }
                    if (file != null){
                        String fileName = saveImage(file, user.getId());
                        userToUpdate.setAvatar(fileName);
                    }
                    userService.updateUser(userToUpdate);
                } catch (IOException | CustomException e) {
                    throw new RuntimeException(e);
                }
            }else {
                throw new CustomException("You have no authorities", HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }
        }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }


    protected String saveImage(MultipartFile file, Long userId) throws IOException {
            byte[] bytes = file.getBytes();
            String userPath = "D:/Games/Projects/Train-o-Gram/src/main/resources/static/" + userId + "/";
            new File(userPath).mkdir();

        Path path = Paths.get(userPath + file.getOriginalFilename());
        Files.write(path, bytes);
        return file.getOriginalFilename();

    }

    @Override
    public byte[] getAvatar(Long id) throws CustomException {

        User user = userService.findUserById(id);

        String path = "/static/"+id+"/"+user.getAvatar();

        boolean boo = new File(path).exists();
        if (!boo) {
            path = "/static/user_picture.jpg";
        }

        InputStream in = getClass().getResourceAsStream(path);
        assert in != null;
        byte[] result = null;
        try {
            result = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NotificationResponseDto> getAllNotification() throws CustomException {
        User user = userService.findAuthenticatedUser();

        List<Notification> notifications = notificationService.getAllNotification(user);
//        if (!notifications.isEmpty()){
        Type listType = new TypeToken<List<NotificationResponseDto>>(){}.getType();
        return mapToDto.map(notifications,listType);
//        }
//        else {
//
//        }

    }

    @Override
    public NotificationResponseDto getNotificationById(Long notificationId) throws CustomException {
        return mapToDto.map(notificationService.getNotificationById(notificationId), NotificationResponseDto.class);
    }
}
