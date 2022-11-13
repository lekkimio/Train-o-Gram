package com.example.trainogram.repository;

import com.example.trainogram.model.Subscription;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT f FROM Subscription f WHERE f.owner = ?1")
    List<User> findByUserId(Long userId);

    @Query("SELECT f.friend FROM Subscription f where f.owner.id = ?1 and f.status = 'MUTUAL'")
    List<User> findAllFriendsByOwnerId(Long userId);

    @Query("SELECT f.friend FROM Subscription f where f.owner.id = ?1 and f.status = 'REQUEST'")
    List<User> findAllRequestByOwnerId(Long userId);

    @Query("SELECT f FROM Subscription f WHERE f.owner = ?1 AND f.friend = ?2")
    Optional<Subscription> findByOwnerAndFriend(User owner, User friend);
}