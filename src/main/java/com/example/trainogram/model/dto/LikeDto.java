package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeDto implements Serializable {

    private Long postId;
    private UserDto user;
}
