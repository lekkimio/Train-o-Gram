package com.example.trainogram.model.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendshipResponseDto implements Serializable {

    private UserResponseDto owner;
    private UserResponseDto friend;
    private String status;

}