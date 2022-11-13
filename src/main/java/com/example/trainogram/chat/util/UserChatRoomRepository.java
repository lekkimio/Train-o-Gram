package com.example.trainogram.chat.util;

import com.example.trainogram.chat.model.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
}