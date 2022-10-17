package com.example.trainogram.exception;

import com.example.trainogram.model.User;

public class Status441FriendshipNotFound extends ErrorCodeException{

    private final static int CODE = 441;

    public Status441FriendshipNotFound(User owner, User friend) {
        super(CODE, "Friendship Not Found between owner Id: " + owner.getId() + "and friend Id: " + friend.getId());
    }
}
