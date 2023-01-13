package com.example.trainogram.service;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status436UserExistsException;
import com.example.trainogram.exception.Status440NotificationNotFound;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.request.UserAuthDto;
import com.example.trainogram.model.dto.request.UserRequestDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    User createUser(UserAuthDto dto) throws Status436UserExistsException, MessagingException, IOException;

    void deleteUser(String token, Long id) throws IOException, Status434UserNotFound, Status435NoAuthorities;

    void updateUser(String token, UserRequestDto dto, MultipartFile file) throws IOException, Status435NoAuthorities, Status434UserNotFound;

//    List<User> findAllUsers(String token) throws Status434UserNotFound, Status435NoAuthorities;

    List<User> findAllUsers(/*String token*/);

    User findUserById(String token , Long id) throws Status434UserNotFound, Status435NoAuthorities;

    User findById(Long id) throws Status434UserNotFound;

    List<Notification> getAllNotification(String token);

    Notification getNotificationById(String token, Long notificationId) throws Status435NoAuthorities, Status440NotificationNotFound;

//    User findAuthenticatedUser();

    User findAuthenticatedUser(String token);

    void refreshToken(String token, HttpServletResponse response) throws IOException;

    InputStreamResource getAvatar(Long id) throws Status434UserNotFound, IOException;

    List<String> getAllUserEmails();
}
