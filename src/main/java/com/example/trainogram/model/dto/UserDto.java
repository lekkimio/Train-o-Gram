package com.example.trainogram.model.dto;

import com.example.trainogram.model.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private String username;
    private PictureDto avatar;
    private Role role;
}
