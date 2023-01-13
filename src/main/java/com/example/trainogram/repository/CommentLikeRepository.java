package com.example.trainogram.repository;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.CommentLike;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("select l from CommentLike l where l.comment = ?2 and l.user =?1")
    CommentLike findLikeByUserAndComment(User user, Comment comment);

    @Query("select u from CommentLike l LEFT JOIN FETCH User u on l.user.id = u.id where l.comment.id = ?1")
    List<User> findAllUsersToComment(Long commentId);

}