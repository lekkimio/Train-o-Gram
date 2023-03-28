package com.example.trainogram.chat.model;

import com.example.trainogram.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Long> recipientsId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom")
    List<UserChatRoom> userChatRooms;

    @ElementCollection()
    @Column(name = "recipients_id")
    @CollectionTable(name = "chatroom_recipients_id", joinColumns = @JoinColumn(name = "chatroom_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> recipientsId;

//    public List<User> getUsers(){
//        return userChatRooms.stream()
//                .map(UserChatRoom::getUser)
//                .collect(Collectors.toList());
//    }
}
