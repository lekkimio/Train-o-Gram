package com.example.trainogram.chat;

import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.util.ChatFacade;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatFacade chatFacade;


    @GetMapping("/get-chat-message")
    public List<ChatMessage> getChatMessages(Long chatId){
        return null;
    }

    @PostMapping("/create-chat")
    public List<Long> createChat(Long recipientId,
                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) throws Status434UserNotFound {
        return chatFacade.createChat(recipientId, token);
    }

    @DeleteMapping("/delete-chat")
    public void deleteChat(Long chatId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
        chatFacade.deleteChat(chatId, token);
    }



}
