package com.example.trainogram.chat.util;


import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.model.ChatMessageFE;
import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.chat.model.ChatRoomFE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ChatFEConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ChatRoomFE convertChatRoomToFE(ChatRoom chatRoom) {
        return modelMapper.map(chatRoom,ChatRoomFE.class);
    }


    public ChatRoom convertFeToChatRoom( ChatRoomFE chatRoomFE ) {
        return modelMapper.map(chatRoomFE,ChatRoom.class);
    }

    public ChatMessageFE convertChatMessageToFe(ChatMessage chatMessage) {
                return modelMapper.map(chatMessage, ChatMessageFE.class);
    }
}
