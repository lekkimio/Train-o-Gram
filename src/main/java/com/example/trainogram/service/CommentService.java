package com.example.trainogram.service;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.User;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment);

    void deleteComment(Long comment);

    List<Comment> getAllComments(Long postId);

    void update(Comment comment);

    Comment findCommentById(Long commentId);
}
