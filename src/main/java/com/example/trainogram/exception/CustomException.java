package com.example.trainogram.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

    private HttpStatus status;

    private Integer code;

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public CustomException(String message, HttpStatus status, Integer code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;}

    public HttpStatus status() {
        return status;
    }

    public Integer code() {
        return code;
    }
}
