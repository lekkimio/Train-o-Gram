package com.example.trainogram.controller;

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


    private final CustomUserDetailsService userDetailsService;

    public AppController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    private String getHomePage(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token){
        String t = token.substring("Bearer ".length());
        System.out.println(t);
        String username = Jwts.parser().setSigningKey("somesecretstring").parseClaimsJws(t).getBody().getSubject();;
        System.out.println(username);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails);
        System.out.println(userDetails.getAuthorities());
        System.out.println(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0));

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
