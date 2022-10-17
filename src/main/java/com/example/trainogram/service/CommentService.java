package com.example.trainogram.service;

import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> getAllComments(String token, Long postId);

    void createComment(String token, String commentText, Long postId) throws Status437PostNotFound;

    void deleteComment(String token, Long commentId) throws Status439CommentNotFound, Status435NoAuthorities;

    Comment findCommentById(Long commentId) throws Status439CommentNotFound;

    void updateComment(String token, String commentText, Long commentId) throws Status439CommentNotFound;

    void updateCommentLikeCount(Comment comment);
}
