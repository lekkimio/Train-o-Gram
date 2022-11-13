package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.model.User;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Long getChatId() {
        Long id = chatRoomRepository.getChatId();
        return id == null ? 1 : id;
    }

    public ChatRoom createChatRoom(Long chatId, User sender, User recipient) {
        if (!chatRoomRepository.existsByChatIdAndSenderIdAndRecipientId(chatId, sender.getId(), recipient.getId())) {
            return chatRoomRepository.save(ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(sender.getId())
                    .recipientId(recipient.getId())
                    .build());
        }

        return null;
    }

    public boolean userIsParticipant(User user, Long chatId) {
        return chatRoomRepository.existsByChatIdAndSenderId(chatId, user.getId());

    }

    public void deleteChat(Long chatId) {
        chatRoomRepository.deleteAllByChatId(chatId);
    }
}
