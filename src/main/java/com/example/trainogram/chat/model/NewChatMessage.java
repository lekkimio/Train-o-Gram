package com.example.trainogram.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewChatMessage {
    private Long chatId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private Integer type;
}
