package com.example.trainogram.service.impl;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentRepository;
import com.example.trainogram.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentPub(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long comment) {
        commentRepository.deleteById(comment);
    }

    @Override
    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("Comment not found"));
    }
}
