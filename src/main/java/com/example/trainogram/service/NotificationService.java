package com.example.trainogram.service;


import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;

import java.util.List;

public interface NotificationService {


    void sendNotification(User user, String s);

    List<Notification> getAllNotification(User user);

    Notification getNotificationById(Long notificationId) throws CustomException;
}
