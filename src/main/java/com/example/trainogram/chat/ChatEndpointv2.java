//package com.example.trainogram.chat;
//
//import com.example.trainogram.chat.model.Message;
//import com.example.trainogram.chat.config.MessageDecoder;
//import com.example.trainogram.chat.config.MessageEncoder;
//
//import javax.websocket.EncodeException;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@ServerEndpoint(value = "/chat",decoders = MessageDecoder.class, encoders = MessageEncoder.class)
//public class ChatEndpointv2 {
//
//
//    public Session session;
//    private static Set<ChatEndpointv2> chatEndpoints= new CopyOnWriteArraySet<>();
//    private static HashMap<String,Session> users = new HashMap<>();
//
//    @OnOpen
//    public void onOpen(Session session, @PathParam("usernameFrom") String usernameFrom){
//        this.session=session;
//        chatEndpoints.add(this);
//        users.put(usernameFrom,session);
//    }
//    @OnMessage
//    public void onMessage(Message message,
//                          @PathParam("usernameTo") String usernameTo,
//                          @PathParam("usernameFrom") String usernameFrom) throws IOException, EncodeException {
//        message.setFrom(usernameFrom);
//        message.setTo(usernameTo);
//        broadcast(message);
//    }
//
//
//    private static void broadcast(Message message) throws IOException, EncodeException {
//        users.get(message.getTo()).getBasicRemote().sendObject(message);
//
//    }
//}
