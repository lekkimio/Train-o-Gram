package com.example.trainogram.chat;

import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.config.MessageDecoder;
import com.example.trainogram.chat.config.MessageEncoder;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value = "/chat",decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {



    public Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("chatRoom") Long chatRoom){
        this.session=session;
    }


    @OnMessage
    public void onMessage(ChatMessage message) throws IOException, EncodeException {
        broadcast(message);
    }


    private static void broadcast(ChatMessage message) throws IOException, EncodeException {
       //get session of recipient -> sendMsg to recipient




    }
}
