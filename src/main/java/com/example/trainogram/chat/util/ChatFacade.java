package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.chat.model.UserChatRoom;
import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.User;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ChatFacade {


    private final ChatRoomService chatRoomService;
    private final UserChatRoomService userChatRoomService;
    private final UserService userService;

    private Long chatId = 0L;

    public List<Long> createChat(Long recipientId, String token) throws Status434UserNotFound {

        User sender = userService.findAuthenticatedUser(token);
        User recipient = userService.findById(recipientId);

        chatId = chatRoomService.getChatId()+1;

        ChatRoom requestingUserChatRoom = chatRoomService.createChatRoom(chatId, sender, recipient);
        ChatRoom requestedUserChatRoom = chatRoomService.createChatRoom(chatId, recipient, sender);

        List<UserChatRoom> userChatRoomList = new ArrayList<>();
        userChatRoomList.add(userChatRoomService.createUserRoom(sender, requestingUserChatRoom));
        userChatRoomList.add(userChatRoomService.createUserRoom(recipient, requestedUserChatRoom));

        requestingUserChatRoom.setUserChatRooms(userChatRoomList);
        requestedUserChatRoom.setUserChatRooms(userChatRoomList);

        return Arrays.asList(chatId, requestingUserChatRoom.getId());
    }

    public void deleteChat(Long chatId, String token) {
        User user = userService.findAuthenticatedUser(token);

        if (chatRoomService.userIsParticipant(user,chatId)){
            chatRoomService.deleteChat(chatId);
        }

    }
}
