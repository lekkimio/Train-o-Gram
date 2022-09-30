package com.example.trainogram.service.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.RequestStatus;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.FriendshipRepository;
import com.example.trainogram.service.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship addFriend(Friendship friendship, boolean friendshipExistence) {
        if (friendshipExistence){
            Friendship otherFriendship = Friendship.builder()
                    .owner(friendship.getFriend())
                    .friend(friendship.getOwner()).build();
            friendship.setStatus(RequestStatus.FRIEND.name());
            otherFriendship.setStatus(RequestStatus.FRIEND.name());
            friendshipRepository.save(otherFriendship);
        }
        return friendshipRepository.save(friendship);

    }

    @Override
    public Friendship findByOwnerAndFriend(User owner, User friend) {
//        Friendship friendship = friendshipRepository.findByUserAndFriend(friend, owner);
//        return friendship != null && friendship.getStatus().equals(RequestStatus.FRIEND);
        return friendshipRepository.findByOwnerAndFriend(owner,friend);
    }

    @Override
    public void deleteFriend(User owner, User friend) throws CustomException {
         Friendship friendship = friendshipRepository.findByOwnerAndFriend(owner, friend);
        if (friendship != null) {
            if (friendship.getStatus().equals(RequestStatus.FRIEND.name())) {
                Friendship otherFriendship = friendshipRepository.findByOwnerAndFriend(friend, owner);
                otherFriendship.setStatus(RequestStatus.REQUEST.name());
                friendshipRepository.save(otherFriendship);
            }
            friendshipRepository.delete(friendship);
        }else {throw new CustomException("Friendship not found", HttpStatus.NOT_FOUND);
        }
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
