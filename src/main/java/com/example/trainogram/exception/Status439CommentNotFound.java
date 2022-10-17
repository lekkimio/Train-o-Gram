package com.example.trainogram.exception;

public class Status439CommentNotFound extends  ErrorCodeException{

    private final static int CODE = 439;

    public Status439CommentNotFound(Long id) {
        super(CODE, "Comment Not Found by id: " + id);
    }

}
