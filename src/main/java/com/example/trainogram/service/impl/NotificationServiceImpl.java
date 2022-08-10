package com.example.trainogram.service.impl;

import com.example.trainogram.model.User;
import com.example.trainogram.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public String sendNotification(User user, String message) {
        return "Notification sent to " + user.getUsername() + ": " + message;
    }
}
