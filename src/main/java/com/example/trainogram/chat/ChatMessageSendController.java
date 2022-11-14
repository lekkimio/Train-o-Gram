package com.example.trainogram.chat;

import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.model.ChatNotification;
import com.example.trainogram.chat.model.NewChatMessage;
import com.example.trainogram.chat.util.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public class ChatMessageSendController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message/send")
    public void processMessage(@Payload NewChatMessage chatMessage) {


        List<ChatMessage> saved = chatService.save(chatMessage);

        saved.forEach(chatMessage1 -> messagingTemplate.convertAndSendToUser(
                chatMessage1.getRecipientId().toString(),"/queue/messages",
                new ChatNotification(
                        chatMessage1.getId(),
                        chatMessage1.getSenderId(),
                        chatMessage1.getChatId())));

    }

}
