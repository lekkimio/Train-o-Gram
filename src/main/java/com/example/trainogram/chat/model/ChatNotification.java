package com.example.trainogram.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private Long id;
    private Long senderId;
    private Long chatId;
}