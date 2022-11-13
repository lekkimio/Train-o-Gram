package com.example.trainogram.service;


import com.example.trainogram.exception.Status440NotificationNotFound;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;

import java.util.List;

public interface NotificationService {


    void sendNotification(User user, String s, String s1);

    List<Notification> getAllNotification(User user);

    Notification getNotificationById(Long notificationId) throws Status440NotificationNotFound;
}
