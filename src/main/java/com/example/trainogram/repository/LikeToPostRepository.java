package com.example.trainogram.repository;

import com.example.trainogram.model.LikeToPost;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeToPostRepository extends JpaRepository<LikeToPost, Long> {

    @Query("select l from LikeToPost l where l.post = ?2 and l.like.user =?1")
    LikeToPost findLikeByUserAndPost(User user, Post post);

    @Query("select p from LikeToPost l LEFT JOIN FETCH Post p on l.post.id = p.id where l.like.user = ?1")
    List<Post> findAllPostsByUser(User user);

    @Query("select u from LikeToPost l LEFT JOIN FETCH User u on l.like.user.id = u.id where l.post.id = ?1")
    List<User> findAllUsersToPost(Long postId);
}