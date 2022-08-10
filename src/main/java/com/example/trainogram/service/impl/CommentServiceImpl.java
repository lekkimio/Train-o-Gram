package com.example.trainogram.service.impl;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentRepository;
import com.example.trainogram.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(User user, Post post, String text) {
        Comment comment = new Comment(null, user, text, LocalDateTime.now(), post.getId());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(User user, Post post, Comment comment) {
        if (comment.getCommentAuthor().equals(user) && comment.getPostId().equals(post.getId())) {
            commentRepository.delete(comment);
        }
    }
}
