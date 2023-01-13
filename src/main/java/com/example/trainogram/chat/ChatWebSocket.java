package com.example.trainogram.chat;

import com.example.trainogram.chat.config.MessageDecoder;
import com.example.trainogram.chat.config.MessageEncoder;
import com.example.trainogram.chat.config.SpringContext;
import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.model.ChatMessageStatus;
import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.chat.util.ChatService;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.User;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat/{chatId}",
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class)
@Slf4j
public class ChatWebSocket {
    private static final Set<ChatWebSocket> CHAT_WEB_SOCKETS = new CopyOnWriteArraySet<>();
    private Session session;


    private User currentUser;
    private ChatRoom currentChat;

    private final ChatService chatService;
    public ChatWebSocket() {
        this.chatService = (ChatService)
                SpringContext.getApplicationContext().getBean("chatService");
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") Long chatId) throws Status434UserNotFound {
        this.session = session;

        this.currentUser = chatService.getCurrentUser(session.getUserPrincipal().getName());
//        this.currentChat = chatService.getChatroomById(chatroomId);
        this.currentChat = chatService.getChatRoomByChatIdAndSenderId(chatId, currentUser.getId());

        boolean flag = true;

        /*for (User member : currentChat.getUsers()) {
            if (Objects.equals(member.getId(), currentUser.getId())) {
                flag = false;
                break;
            }
        }
        if (flag)
            throw new IllegalArgumentException(currentChat.getChatId().toString());*/
//            throw new Status404DeniedAccessException("The user is not a member of the chat with id = " + currentChat.getId());

        CHAT_WEB_SOCKETS.add(this);

        List<ChatMessage> messages = chatService.getChatMessages(currentChat.getChatId());

        for (ChatMessage message : messages) {
            broadcastToChat(message);
        }
    }

    private void broadcastToChat(ChatMessage message) {
        CHAT_WEB_SOCKETS.forEach(endpoint -> {
            synchronized (this) {
                if (Objects.equals(this.currentChat.getId(), endpoint.currentChat.getId())) {
                        try {
                            endpoint.session.getBasicRemote().
                                    sendObject(message);
                        } catch (IOException | EncodeException e) {
                            e.printStackTrace();
                        }
                }
            }
        });
    }

    @OnMessage
    public void onMessage(String message)  {
        sendMessage(message);
    }

    @OnClose
    public void onClose() {
        CHAT_WEB_SOCKETS.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        log.error(throwable.toString());
    }

    public void sendMessage(String message) {

        ChatMessage messageObj = ChatMessage.builder()
                .chatId(currentChat.getChatId())
                .content(message)
                .createdDate(new Date())
                .recipientId(currentChat.getRecipientId())
                .senderId(currentChat.getSenderId())
                .status(ChatMessageStatus.SENDING)
                .build();

        chatService.sendMessage(messageObj);

        broadcast(ChatMessage.builder()
                .id(messageObj.getId())
                .chatId(messageObj.getChatId())
                .senderId(messageObj.getSenderId())
                .recipientId(messageObj.getRecipientId())
                .content(messageObj.getContent())
                .createdDate(messageObj.getCreatedDate())
                .build());

    }

    private void broadcast(ChatMessage messageDto) {
        CHAT_WEB_SOCKETS.forEach(endpoint -> {
            synchronized (this) {
                if (Objects.equals(this.currentChat.getChatId(), endpoint.currentChat.getChatId())) {
                    if (!Objects.equals(this.currentUser.getId(), endpoint.currentUser.getId())) {
                        try {
                            endpoint.session.getBasicRemote().
                                    sendObject(messageDto);
                        } catch (IOException | EncodeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

}