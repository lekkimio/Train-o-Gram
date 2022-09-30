package com.example.trainogram.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AppController {

    @GetMapping("/")
    private String getHomePage(){
        return "hello" +" "+ SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/admin")
    private String getAdminPage(){
        return "hello" +" "+ SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
