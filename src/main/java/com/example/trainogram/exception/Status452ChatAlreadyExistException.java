package com.example.trainogram.exception;

public class Status452ChatAlreadyExistException extends ErrorCodeException {
    private final static int CODE = 452;

    public Status452ChatAlreadyExistException() {
        super(CODE, "Chat Already Exist");
    }
}
