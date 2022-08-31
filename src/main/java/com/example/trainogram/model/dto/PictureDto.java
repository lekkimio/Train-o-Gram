package com.example.trainogram.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PictureDto implements Serializable {

    private Long id;
    private LocalDateTime created;
}
