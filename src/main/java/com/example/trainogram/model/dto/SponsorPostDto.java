package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SponsorPostDto implements Serializable {

    private final PostDto post;
    private final UserDto user;
}
