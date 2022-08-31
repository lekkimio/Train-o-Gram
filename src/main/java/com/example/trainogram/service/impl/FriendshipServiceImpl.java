package com.example.trainogram.service.impl;

import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.RequestStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FriendshipRepository;
import com.example.trainogram.service.FriendshipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship addFriend(User owner, User friend) {
        Friendship newFriendship = new Friendship();
        newFriendship.setOwner(owner);
        newFriendship.setFriend(friend);
        Friendship friendship = friendshipRepository.findByUserAndFriend(friend,owner);
        if (friendship != null) {
            newFriendship.setStatus(RequestStatus.FRIEND.name());
            friendship.setStatus(RequestStatus.FRIEND.name());
            friendshipRepository.save(friendship);
        } else {
            newFriendship.setStatus(RequestStatus.REQUEST.name());
        }
        return friendshipRepository.save(newFriendship);


    }

    @Override
    public void deleteFriend(User owner, User friend) {
        Friendship friendship = friendshipRepository.findByUserAndFriend(owner, friend);
        if(friendship.getStatus().equals(RequestStatus.FRIEND.name())) {
            Friendship otherFriendship = friendshipRepository.findByUserAndFriend(friend, owner);
            otherFriendship.setStatus(RequestStatus.REQUEST.name());
            friendshipRepository.save(otherFriendship);
        }
        friendshipRepository.delete(friendship);
        System.out.println("Friendship deleted");
    }

    @Override
    public List<User> findAllFriends(Long userId) {
        return friendshipRepository.findAllFriendsByOwnerId(userId);
    }

    @Override
    public List<User> findAllRequests(Long userId) {
        return friendshipRepository.findAllRequestByOwnerId(userId);
    }
}
