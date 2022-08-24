package com.example.trainogram.repository;

import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE f.owner = ?1")
    List<User> findByUserId(Long userId);

    @Query("SELECT f.friend FROM Friendship f where f.owner.id = ?1 and f.status = 'FRIEND'")
    List<User> findAllFriendsByOwnerId(Long userId);

    @Query("SELECT f.friend FROM Friendship f where f.owner.id = ?1 and f.status = 'REQUEST'")
    List<User> findAllRequestByOwnerId(Long userId);

    @Query("SELECT f FROM Friendship f WHERE f.owner = ?1 AND f.friend = ?2")
    Friendship findByUserAndFriend(User user, User friend);
}