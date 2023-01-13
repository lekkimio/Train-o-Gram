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
@Table(name = "comment_likes")
public class CommentLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_ID")
    private Comment comment;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_ID")
    private User user;

}
