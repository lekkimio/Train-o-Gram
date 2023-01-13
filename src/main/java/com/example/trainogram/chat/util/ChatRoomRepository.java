package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT chatId from ChatRoom where chatId=(SELECT MAX(chatId) from ChatRoom )")
    Long getMaxChatId();

    Optional<List<ChatRoom>> findAllByChatId(Long chatId);

    @Query("select c from ChatRoom c where c.chatId=?1 and c.senderId=?2")
    Optional<ChatRoom> findByChatIdaAndSenderId(Long chatId, Long senderId);

    @Query("select s.senderId from ChatRoom s where s.id=?1")
    Long findSenderById(Long chatRoomId);

    ChatRoom getChatRoomByChatIdAndSenderId(Long chatId, Long senderId);
}