package com.example.trainogram.controller;

import com.example.trainogram.security.CustomUserDetails;
import com.example.trainogram.security.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class AppController {

    @GetMapping("/")
    private String getHomePage(){
        return "hello" +" "+ SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/test")
    private String getHomePage(CustomUserDetails userDetails){
        return "hello" +" "+ userDetails.getUsername();
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
