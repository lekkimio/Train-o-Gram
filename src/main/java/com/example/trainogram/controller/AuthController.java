package com.example.trainogram.controller;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.UserFacade;
import com.example.trainogram.model.dto.request.UserAuthDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserFacade userFacade;

    public AuthController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /*@GetMapping("/login")
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
    }*/

    @PostMapping("/signup")
    public void signupProcess(@RequestBody UserAuthDto user) throws  IOException, CustomException {
        userFacade.createUser(user);
    }



}
