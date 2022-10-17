package com.example.trainogram.controller;

import com.example.trainogram.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/home")
//    private String getHomePage(Model model){
//        User user = userService.findAuthenticatedUser();
//        model.addAttribute("myUser", user);
//        return "home";
//    }


}
