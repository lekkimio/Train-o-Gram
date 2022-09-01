package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
import com.example.trainogram.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UserFacadeImpl implements UserFacade {


    private final UserService userService;

    private ModelMapper mapToDto;

    @Value("${admin.key}")
    private String adminKey;

    private String folder = "D:\\Games\\Projects\\PictureUploadTest\\src\\main\\resources\\static\\";

    public UserFacadeImpl(UserService userService, ModelMapper mapToDto) {
        this.userService = userService;
        this.mapToDto = mapToDto;
    }


    @Override
    public UserDto findUserById(Long id) throws UserException {
        User user = userService.findUserById(id);
        return mapToDto.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() throws UserException {
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
    public UserDto addUser(User user, String key) throws UserException, IOException {
        if (Objects.equals(key, adminKey)){
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
//        if (file != null){
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(folder+user.getId().toString()+ File.separator+file.getOriginalFilename());
//            Files.write(path,bytes);
//
//            user.setAvatar(file.getOriginalFilename());
//        }
        User savedUser = userService.addUser(user);
        return mapToDto.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, User user, MultipartFile file, String key) throws UserException {
        User userToUpdate = userService.findUserById(id);
        if (key!=null && key.equals(adminKey)){
            userToUpdate.setRole(Role.ADMIN);
        }
        if (file != null) {
            try {
                saveImage(file,id);
                userToUpdate.setAvatar(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return mapToDto.map(userService.updateUser(userToUpdate), UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }


    protected String saveImage(MultipartFile file, Long userId) throws IOException {
        byte[] bytes = file.getBytes();
        String userPath = folder+userId+File.separator;
        File file1 = new File(userPath);
        file1.mkdir();
        Path path = Paths.get(userPath+file.getOriginalFilename());
        Files.write(path,bytes);
        return path.toString();
    }
}
