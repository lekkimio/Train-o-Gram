package com.example.trainogram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_ID")
    private Post post;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_ID")
    private User user;

}
