package com.example.trainogram.exception;

public class Status438SponsorPostNotFound extends ErrorCodeException{
    private static final int CODE = 438;

    public Status438SponsorPostNotFound(Long id) {
        super(CODE, "Sponsor Post Not Found by id: " + id);
    }

}
