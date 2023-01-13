package com.example.trainogram.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SponsorReport {

    private Long postId;
    private Integer likes;
    private Integer comments;
}
