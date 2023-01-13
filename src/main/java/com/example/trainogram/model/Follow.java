package com.example.trainogram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_ID")
    private User owner;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "friend_ID")
    private User friend;

//    private String status;

}
