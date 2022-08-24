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

    @Query("select u from Like l LEFT JOIN FETCH User u on l.user.id = u.id where l.post.id = ?1")
    List<User> findUserByPostId(Long postId);

    @Query("select p from Like l LEFT JOIN FETCH Post p on l.post.id = p.id where l.user.id = ?1")
    List<Post> findAllPostsLikedByUser(Long userId);
}
