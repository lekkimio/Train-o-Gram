package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatRoom;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT chatId from ChatRoom where chatId=(SELECT MAX(chatId) from ChatRoom )")
    Long getChatId();

    boolean existsByChatIdAndSenderIdAndRecipientId(Long chatId, Long senderId, Long recipientId);

    boolean existsByChatIdAndSenderId(Long chatId, Long senderId);

    void deleteAllByChatId(Long chatId);
}