package com.example.trainogram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Integer likes = 0;


    @OneToOne
    @JoinColumn(name = "post_author_id")
    private User postAuthor;

    @OneToOne
    @JoinColumn(name = "post_picture_id")
    private Picture postPicture;

    private String postText;

    private LocalDateTime pubDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comment> comments;


}
