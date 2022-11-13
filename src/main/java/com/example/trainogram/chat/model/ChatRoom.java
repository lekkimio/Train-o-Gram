package com.example.trainogram.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long chatId;

    private Long senderId;

    private Long recipientId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRooms = new java.util.ArrayList<>();

}
