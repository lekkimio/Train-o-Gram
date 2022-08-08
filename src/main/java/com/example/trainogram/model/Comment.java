package com.example.trainogram.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private User commentAuthor;
    private String text;
    private Date commentPub;


}
