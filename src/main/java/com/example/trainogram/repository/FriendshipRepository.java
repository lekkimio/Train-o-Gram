package com.example.trainogram.repository;

import com.example.trainogram.model.Friendship;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


    @Query("SELECT f FROM Friendship f WHERE f.owner = ?1 AND f.friend = ?2")
    Friendship getFriendshipByUserAndFriend(User user, User friend);
}