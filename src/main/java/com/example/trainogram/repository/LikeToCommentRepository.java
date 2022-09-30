package com.example.trainogram.repository;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.LikeToComment;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeToCommentRepository extends JpaRepository<LikeToComment, Long> {

    LikeToComment findLikeToPostByCommentAndUser(Comment comment, User user);

    @Query("select u from LikeToComment l LEFT JOIN FETCH User u on l.user.id = u.id where l.comment.id = ?1")
    List<User> findAllUsersToComment(Long commentId);
}