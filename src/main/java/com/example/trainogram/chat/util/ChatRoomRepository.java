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
    Long getChatId();

    Optional<List<ChatRoom>> findAllByChatId(Long chatId);

    Optional<ChatRoom> findByChatId(Long chatId);
}