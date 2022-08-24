package com.example.trainogram.controller;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping()
    public List<User> getAllUsers() throws UserNotFoundException {
        return userFacade.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        return userFacade.findUserById(id);
    }

    @PostMapping("/{key}")
    public User addUser(@RequestBody User user, @PathVariable String key) throws UserNotFoundException {
        return userFacade.addUser(user, key);
    }

    @PutMapping("/{id}/{key}")
    public User updateUser(@PathVariable Long id, @RequestBody User user, @PathVariable String key) throws UserNotFoundException {
        return userFacade.updateUser(id, user, key);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }


}
