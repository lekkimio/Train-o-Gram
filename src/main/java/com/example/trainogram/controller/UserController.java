package com.example.trainogram.controller;

import com.example.trainogram.exception.UserNotFoundException;
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
    public List<UserDto> getAllUsers() throws UserNotFoundException {
        return userFacade.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws UserNotFoundException {
        return userFacade.findUserById(id);
    }

    @PostMapping()
    public UserDto addUser(@RequestBody User user, @RequestParam(required = false) String key, MultipartFile file) throws UserNotFoundException, IOException {
        return userFacade.addUser(user, file, key);
    }

    @PutMapping("/{id}/{key}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody User user, @PathVariable String key) throws UserNotFoundException {
        return userFacade.updateUser(id, user, key);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }


}
