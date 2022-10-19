package com.example.trainogram.security.jwt;

import com.example.trainogram.exception.Status450JwtException;
import com.example.trainogram.security.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    private final CustomUserDetailsService userDetailsService;

    public CustomAuthorizationFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //ToDO:
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (request.getServletPath().equals("/auth/login")  || request.getServletPath().equals("/auth/token/refresh")){
                filterChain.doFilter(request,response);
            }else {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                    try {
                    String token = authorizationHeader.substring("Bearer ".length());
                        validateToekn(token);

                        String username = Jwts.parser().setSigningKey("somesecretstring").parseClaimsJws(token).getBody().getSubject();;
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


//                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                        stream(roles).forEach(role -> {
//                            authorities.add(new SimpleGrantedAuthority(role));
//                        });

//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(username,null, );
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                    }catch (Exception exception){
                        log.error("Error logging in: {}", exception.getMessage());
                        response.setHeader("error", exception.getMessage());
                        response.setStatus(FORBIDDEN.value());
//                        response.sendError(FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error_message", exception.getMessage());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }
                }
                else {
                    filterChain.doFilter(request,response);
                }
            }
    }

    private boolean validateToekn(String token) throws Status450JwtException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("somesecretstring").parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            throw new Status450JwtException();
        }
    }
}
