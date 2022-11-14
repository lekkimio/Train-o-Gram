package com.example.trainogram.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageFE {

    private Long id;

    private Long chatId;

    private Long senderId;

    private Long recipientId;

    private String content;

    private Date createdDate;
}
