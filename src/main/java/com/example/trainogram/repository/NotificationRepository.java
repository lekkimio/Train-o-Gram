package com.example.trainogram.repository;

import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n from Notification n where n.user = ?1 ")
    List<Notification> findAllByUser(User user);
}