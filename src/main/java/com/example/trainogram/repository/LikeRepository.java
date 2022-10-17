package com.example.trainogram.repository;

import com.example.trainogram.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {



    @Query("select l from LikeToPost l where l.post = ?2 and l.like.user =?1")
    LikeToPost findLikeByUserAndPost(User author,Post post);

    @Query("select p from LikeToPost l LEFT JOIN FETCH Post p on l.post.id = p.id where l.like.user = ?1")
    List<Post> findAllPostsByUser(User user);

    @Query("select u from LikeToPost l LEFT JOIN FETCH User u on l.like.user.id = u.id where l.post.id = ?1")
    List<User> findAllUsersToPost(Long postId);

    @Query("select l from LikeToComment l where l.comment = ?1 and l.like.user =?2")
    LikeToComment findLikeToPostByCommentAndUser(Comment comment, User author);

    @Query("select u from LikeToComment l LEFT JOIN FETCH User u on l.like.user.id = u.id where l.comment.id = ?1")
    List<User> findAllUsersToComment(Long commentId);

}