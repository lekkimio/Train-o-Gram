package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status440NotificationNotFound;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.NotificationStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.NotificationRepository;
import com.example.trainogram.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(User user, String s, String s1) {
        notificationRepository.save(Notification.builder()
                .user(user)
                .message(s)
                .dateOfReceiving(LocalDateTime.now())
                .status(NotificationStatus.UNSEEN)
                        .link(s1)
                .build());
    }

    @Override
    public List<Notification> getAllNotification(User user) {
        /*List<Notification> notifications = notificationRepository.findAllByUser(user);
        for (Notification n: notifications) {
            n.setStatus(NotificationStatus.SEEN);
        }
        return notificationRepository.saveAll(notifications);*/
        return notificationRepository.findAllByUser(user);
    }

    @Override
    public Notification getNotificationById(Long notificationId) throws Status440NotificationNotFound {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isPresent()) {
            notification.get().setStatus(NotificationStatus.SEEN);
            return notificationRepository.save(notification.get());

        } else throw new Status440NotificationNotFound(notificationId);
    }
}
