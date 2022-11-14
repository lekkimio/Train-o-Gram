package com.example.trainogram.chat.model;

import com.example.trainogram.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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
    List<UserChatRoom> userChatRooms;


    public List<User> getUsers(){
        return userChatRooms.stream()
                .map(UserChatRoom::getUser)
                .collect(Collectors.toList());
    }
}
