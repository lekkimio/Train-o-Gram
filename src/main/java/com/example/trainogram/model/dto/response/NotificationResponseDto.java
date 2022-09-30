package com.example.trainogram.model.dto.response;

import com.example.trainogram.model.NotificationStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class NotificationResponseDto implements Serializable {
    private String message;
    private LocalDateTime dateOfReceiving;
    private NotificationStatus status;
}