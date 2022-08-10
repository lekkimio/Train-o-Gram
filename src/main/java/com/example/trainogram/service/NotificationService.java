package com.example.trainogram.service;

import com.example.trainogram.model.User;

public interface NotificationService {

    String sendNotification(User user, String message);
}
