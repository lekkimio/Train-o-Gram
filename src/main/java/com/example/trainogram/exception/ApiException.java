package com.example.trainogram.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
public record ApiException(String message, Integer code, ZonedDateTime timestamp) {

}
