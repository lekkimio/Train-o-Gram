package com.example.trainogram.repository;

import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {


    Like getLikeByUserAndPost(User user, Post post);

    @Query(value = "SELECT * FROM likes WHERE post_id = ?1", nativeQuery = true)
    List<Like> findAllByPostId(Post post);
}