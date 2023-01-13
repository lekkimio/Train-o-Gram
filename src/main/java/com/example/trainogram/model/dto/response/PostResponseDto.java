package com.example.trainogram.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class PostResponseDto implements Serializable {
    private Long id;
    private Integer likes;
    private UserResponseDto postAuthor;
    private String postText;
    private LocalDateTime pubDate;
    private List<String> picture;
}
