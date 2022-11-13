package com.example.trainogram.service;


import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status441FriendshipNotFound;
import com.example.trainogram.exception.Status449FriendshipIllegalRequest;
import com.example.trainogram.model.Subscription;
import com.example.trainogram.model.User;

import java.util.List;

public interface SubscriptionService {

    void createSubscription(String token, Long id) throws Status441FriendshipNotFound, Status434UserNotFound, Status449FriendshipIllegalRequest;

    void deleteSubscribtion(String token, Long id) throws Status434UserNotFound, Status441FriendshipNotFound;

    List<User> findAllRequests(Long id);

    List<User> findAllSubscribers(Long id);

    Subscription findByOwnerAndFriend(User owner, User friend) throws Status441FriendshipNotFound;
}
