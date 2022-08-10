package com.example.trainogram.service.impl;

import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FriendshipRepository;
import com.example.trainogram.service.FriendshipService;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public void addFriend(User user, User friend) {
        Friendship friendship = new Friendship();
        friendship.setOwner(user);
        friendship.setFriend(friend);
        friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        Friendship friendship = friendshipRepository.getFriendshipByUserAndFriend(user, friend);
        friendshipRepository.delete(friendship);
    }
}
