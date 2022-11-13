package com.example.trainogram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer likes;


    @OneToOne
    @JoinColumn(name = "post_author_id")
    private User postAuthor;

    private String postPicture;

    private String postText;

    private LocalDateTime pubDate;

    @OneToMany()
    private List<Comment> comments;

}
