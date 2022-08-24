package com.example.trainogram.controller;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.User;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserFacade userFacade;

    public AuthController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup/{key}")
    public String signupProcess(@RequestBody User user, @PathVariable String key) throws UserNotFoundException {
        userFacade.addUser(user, key);
        return "redirect:localhost:8080/users";
    }



}
