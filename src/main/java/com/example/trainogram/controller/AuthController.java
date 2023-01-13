package com.example.trainogram.controller;

import com.example.trainogram.exception.Status436UserExistsException;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public User signupProcess(@RequestBody UserAuthDto user) throws Status436UserExistsException, MessagingException, IOException {
        return userService.createUser(user);
    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, HttpServletResponse response) throws IOException {
//        userService.refreshToken(token,response);
//    }

}
