package com.example.trainogram.chat;

import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.util.ChatService;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status452ChatAlreadyExistException;
import com.example.trainogram.exception.Status453ChatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @PostMapping("/create-chat")
    public Long createChat(@RequestParam List<Long> recipientId,
                           @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) throws Status434UserNotFound, Status452ChatAlreadyExistException {
        return chatService.createNewChat(recipientId, token);
    }

//    @PostMapping("/create-group-chat")
//    public Long createChat(List<Long> recipientId,
//                           @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) throws Status434UserNotFound, Status452ChatAlreadyExistException {
//        return chatService.createNewGroupChat(recipientId, token);
//    }

    @GetMapping("/messages/{chatId}")
    public List<ChatMessage> getChatMessage(
            @PathVariable Long chatId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return chatService.findChatMessagesAndMarkDelivered(chatId, token);
    }

//    @DeleteMapping("/delete/{chatId}")
//    public void deleteChat(@PathVariable Long chatId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) throws Status453ChatNotFoundException {
//        chatService.deleteChat(chatId, token);
//
//    }



}
