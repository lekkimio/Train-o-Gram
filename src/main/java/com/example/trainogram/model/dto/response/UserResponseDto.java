package com.example.trainogram.model.dto.response;

import com.example.trainogram.model.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponseDto implements Serializable {

    private String username;
    private String avatar;
//    private Role role;

}
