package com.example.trainogram.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status436UserExistsException;
import com.example.trainogram.exception.Status440NotificationNotFound;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.DeletingService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import com.example.trainogram.service.email.EmailSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;
    private final PictureService pictureService;
    private final EmailSender emailSender;

    private final Algorithm algorithm = Algorithm.HMAC256("somesecretstring".getBytes());


    @Value("${token.expiration}")
    private int tokenExpiration;


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User createUser(UserAuthDto dto) throws Status436UserExistsException, MessagingException, IOException {
        if (userRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new Status436UserExistsException(dto.getUsername());
        } else{

             User newUser = userRepository.save(User.builder()
                     .email(dto.getEmail())
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(Role.USER)
                    .avatar("user_picture.jpg").build());
             emailSender.sendEmail(newUser.getEmail(), "Congratulations!", "auth_successful.html");

             return newUser;
        }
    }

    @Override
    public void deleteUser(String token, Long id) throws IOException, Status435NoAuthorities, Status434UserNotFound {
        if (Objects.equals(findAuthenticatedUser(token).getId(), id) || findAuthenticatedUser(token).getRole().equals(Role.ADMIN)) {
// TODO: 26.12.2022 deleting user
            User user = findById(id);
            userRepository.insertUser(user.getAvatar(), user.getPassword(), user.getRole(), user.getUsername(), user.getEmail());
            userRepository.delete(user);
        }else throw new Status435NoAuthorities("delete");
    }

    @Override
    public void updateUser(String token, UserRequestDto dto, MultipartFile file) throws IOException, Status435NoAuthorities,
                                                                                                        Status434UserNotFound {
        User user = findById(dto.getId());
//        if (/*userService.findUserById(user.getId()) != null*/ optionalUser.isPresent()) {
        if (findAuthenticatedUser(token).getId().equals(user.getId())) {
            if (user.getUsername() != null) {
                user.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                user.setPassword(user.getPassword());
            }
            if (file != null) {
                String fileName = pictureService.saveUserImage(file, user.getId());
                user.setAvatar(fileName);
            }
            userRepository.save(user);
        } else {
            throw new Status435NoAuthorities("update");
        }
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAllActiveUsers();
    }

    @Override
    public User findUserById(String token ,Long id) throws Status434UserNotFound, Status435NoAuthorities {
        return findById(id);
    }

    @Override
    public User findById(Long id) throws Status434UserNotFound {
        return userRepository.findById(id).orElseThrow(() -> new Status434UserNotFound(id));
    }

    @Override
    public List<Notification> getAllNotification(String token) {
        User user = findUserByUsername(validateToken(token).getSubject());
        return notificationService.getAllNotification(user);
    }

    @Override
    public Notification getNotificationById(String token, Long notificationId) throws Status435NoAuthorities, Status440NotificationNotFound {
        Notification notification = notificationService.getNotificationById(notificationId);
        if (notification.getUser().equals(findAuthenticatedUser(token))) {
            return notification;
        } else {
            throw new Status435NoAuthorities("get notification");
        }

    }

    @Override
    public User findAuthenticatedUser(String token) {
        return userRepository.findUserByUsername(
                validateToken(token).getSubject());
    }
    protected DecodedJWT validateToken(String token){
       return JWT.decode(token.substring("Bearer ".length()));

    }


    @Override
    public void refreshToken(String token, HttpServletResponse response) throws IOException {


        if (token != null && token.startsWith("Bearer ")) {
            try {
                String refresh_token = token.substring("Bearer ".length());
                User user = findAuthenticatedUser(refresh_token);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                        .withIssuer("http://localhost:8080/auth/login")
                        .withClaim("roles", Collections.singletonList(user.getRole()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch(Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


    @Override
    public InputStreamResource getAvatar(Long id) throws Status434UserNotFound, IOException {
        User user = userRepository.findById(id).orElseThrow(()->new Status434UserNotFound(id));
        return pictureService.getUserAvatar(user);
    }

    @Override
    public List<String> getAllUserEmails() {

        return userRepository.getEmails();
    }
}
