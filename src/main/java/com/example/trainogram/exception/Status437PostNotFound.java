package com.example.trainogram.exception;

public class Status437PostNotFound extends ErrorCodeException{

    private static final int CODE = 437;

    public Status437PostNotFound(String message, String type, String value) {
        super(CODE,message,"Post Not Found by " +type+ ": " + value);
    }

    public Status437PostNotFound(Long id) {
        super(CODE, "Post Not Found by id: " + id);
    }

}
