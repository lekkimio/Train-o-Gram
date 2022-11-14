package com.example.trainogram.chat.model;

import com.example.trainogram.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomFE {
    private Long id;
    private Long chatId;
    private Long senderId;
    private Long recipientId;
    private int newMessageCount;
    private ChatMessageFE lastMessage;
    private List<User> users;

}
