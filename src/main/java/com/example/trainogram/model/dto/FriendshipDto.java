package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendshipDto implements Serializable {

    private UserDto owner;
    private UserDto friend;
    private String status;
}
