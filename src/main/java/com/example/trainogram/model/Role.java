package com.example.trainogram.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ToString
public enum Role {


    USER,
    ADMIN

}

