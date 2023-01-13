package com.example.trainogram.model.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthDto implements Serializable {
    private final String email;
    private final String username;
    private final String password;
}
