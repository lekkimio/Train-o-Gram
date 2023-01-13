package com.example.trainogram.exception;

import com.example.trainogram.model.User;

public class Status449FriendshipIllegalRequest extends ErrorCodeException {

    private final static int CODE = 449;


    public Status449FriendshipIllegalRequest(User owner, User friend) {
        super(CODE,"Friendship already exists between owner Id: " + owner.getId() + " and friend Id: " + friend.getId());

    }

    public Status449FriendshipIllegalRequest() {
        super(CODE,"Friendship cant be created to the same entity ");
    }
}
