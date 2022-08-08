package com.example.trainogram.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Post {

    private Integer likes;
    private User postAuthor;
    private Picture postPicture;
    private Date pubDate;
    public List<Comment> comments;


}
