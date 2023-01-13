package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatMessage;
import com.example.trainogram.chat.model.ChatMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("select count(c) from ChatMessage c")
    Integer countChatMessagesByChatIdAndRecipientIdAndStatus(Long chatId, Long senderId, ChatMessageStatus received);

    ChatMessage findFirstByChatIdOrderByIdDesc(Long chatId);

    List<ChatMessage> findAllByChatIdAndRecipientId(Long chatId, Long id);

    List<ChatMessage> findAllByChatIdOrderByCreatedDate(Long chatId);

}