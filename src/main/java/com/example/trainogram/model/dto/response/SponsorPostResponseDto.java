package com.example.trainogram.model.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SponsorPostResponseDto implements Serializable {

    private PostResponseDto post;
    private UserResponseDto user;
}
