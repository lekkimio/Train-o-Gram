package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentDto implements Serializable {

    private UserDto commentAuthor;
    private String text;
    private LocalDateTime commentPub;
}
