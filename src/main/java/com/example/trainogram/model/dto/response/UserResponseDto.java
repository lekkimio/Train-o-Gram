package com.example.trainogram.model.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponseDto implements Serializable {

    private String username;
    private String avatar;

}
