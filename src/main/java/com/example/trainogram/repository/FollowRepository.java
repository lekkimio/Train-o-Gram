package com.example.trainogram.repository;

import com.example.trainogram.model.Follow;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f FROM Follow f WHERE f.owner = ?1")
    List<User> findByUserId(Long userId);

    @Query("SELECT f.friend FROM Follow f where f.owner.id = ?1")
    List<User> findAllFollowingByOwnerId(Long userId);

    @Query("SELECT f.owner FROM Follow f where f.friend.id = ?1")
    List<User> findAllFollowersByOwnerId(Long userId);

    @Query("SELECT f FROM Follow f WHERE f.owner = ?1 AND f.friend = ?2")
    Optional<Follow> findByOwnerAndFriend(User owner, User friend);
}