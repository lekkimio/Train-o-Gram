package com.example.trainogram.exception;

import com.example.trainogram.model.User;

public class Status440NotificationNotFound extends ErrorCodeException {

    private final static int CODE = 440;

    public Status440NotificationNotFound(Long id) {
        super(CODE, "Notification Not Found for id: " + id);
    }
}
