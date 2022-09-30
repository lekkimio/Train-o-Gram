package com.example.trainogram.model.dto.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class CommentRequestDto implements Serializable {

    private String text;
    private Long postId;

}