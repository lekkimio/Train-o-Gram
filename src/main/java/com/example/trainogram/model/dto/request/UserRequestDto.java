package com.example.trainogram.model.dto.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserRequestDto implements Serializable {
//    private Long id;
    private String username;
    private String password;
    private String avatar;
}