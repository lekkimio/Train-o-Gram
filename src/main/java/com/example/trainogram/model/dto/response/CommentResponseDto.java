package com.example.trainogram.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentResponseDto implements Serializable {

    private Long id;
    private UserResponseDto commentAuthor;
    private String text;
    private Integer likes;
    private LocalDateTime commentPub;
}
