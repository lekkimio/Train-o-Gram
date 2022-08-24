package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CommentException;
import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService commentService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final LikeService likeService;
    private final UserService userService;


    public CommentFacadeImpl(CommentService commentService, PostService postService, NotificationService notificationService, LikeService likeService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.likeService = likeService;
        this.userService = userService;
    }


    @Override
    public List<Comment> getAllComments(Long postId) {
        return commentService.getAllComments(postId);
    }

    @Override
    public void updateComment(Long postId, Long commentId, Comment comment) throws CommentException {
        Comment commentExists = commentService.findCommentById(commentId);
            if (commentExists.getCommentAuthor().equals(userService.findAuthenticatedUser())
                    && commentExists.getPostId().equals(postId)) {
                commentExists.setText(comment.getText());
                commentService.update(commentExists);
            } else {
                throw new CommentException("You can't update this comment");
            }

    }

    @Override
    public Comment addComment(Long postId, Comment comment) {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        comment.setCommentAuthor(user);
        comment.setPostId(post.getId());

        return commentService.addComment(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) throws CommentException {
        Comment comment = commentService.findCommentById(commentId);
        if (comment.getCommentAuthor().equals(userService.findAuthenticatedUser())
                && comment.getPostId().equals(postId)) {
            commentService.deleteComment(commentId);
        } else {
            throw new CommentException("You can't delete this comment");
        }

    }
}
