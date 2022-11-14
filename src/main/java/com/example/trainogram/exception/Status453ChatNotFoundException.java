package com.example.trainogram.exception;

public class Status453ChatNotFoundException extends ErrorCodeException {

    private static final int CODE = 453 ;

    public Status453ChatNotFoundException(Long id) {
        super(CODE, "Chat Not Found by id: " + id);
    }
}
