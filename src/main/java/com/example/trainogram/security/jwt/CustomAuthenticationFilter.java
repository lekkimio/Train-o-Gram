package com.example.trainogram.security.jwt;

import com.example.trainogram.model.User;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    @Value("${token.expiration}")
    private int tokenExpiration;
    @Value("${secret}")
    private String secret;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}", username );
        log.info("Password is: {}", password );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
           org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
           User user = userRepository.findUserByUsername(principal.getUsername());

//        Algorithm algorithm = Algorithm.HMAC256("somesecretstring".getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
//                .withIssuer(request.getRequestURL().toString())
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRole().name());

        Date now = new Date(System.currentTimeMillis());
        String access_token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 10*60*1000))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "somesecretstring")
                .compact();
        String refresh_token = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 30*60*1000))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "somesecretstring")
                .compact();
        /*response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);


    }
}
