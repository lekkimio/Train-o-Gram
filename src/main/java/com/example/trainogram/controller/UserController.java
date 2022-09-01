package com.example.trainogram.controller;

import com.example.trainogram.exception.UserException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.UserDto;
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
    public List<UserDto> getAllUsers() throws UserException {
        return userFacade.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws UserException {
        return userFacade.findUserById(id);
    }

    @PostMapping()
    public UserDto addUser(User user/*, MultipartFile file*/, @RequestParam(required = false) String key) throws UserException, IOException {
        return userFacade.addUser(user,/* file,*/ key);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, User user, MultipartFile file, @RequestParam(required = false) String key) throws UserException {
        return userFacade.updateUser(id, user, file ,key);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }


}
