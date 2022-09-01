package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto implements Serializable {
    private Long id;
    private Integer likes;
    private UserDto postAuthor;
    private String postPicture;
    private String postText;
    private LocalDateTime pubDate;
    private List<CommentDto> comments;
}
