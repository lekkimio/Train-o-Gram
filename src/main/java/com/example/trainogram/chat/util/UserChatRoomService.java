package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.chat.model.UserChatRoom;
import com.example.trainogram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserChatRoomService {

    @Autowired
    private UserChatRoomRepository userChatRoomRepository;

    public UserChatRoom createUserRoom(User user, ChatRoom chatRoom) {
        return userChatRoomRepository.save(
                UserChatRoom.builder()
                        .chatRoom(chatRoom)
                        .user(user)
                .build());
    }
}
