package com.example.trainogram.security;

import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUser {

    public static CustomUserDetails create(User user){
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRoles){
        return Collections.singletonList(new SimpleGrantedAuthority(userRoles.name()));
    }

}
