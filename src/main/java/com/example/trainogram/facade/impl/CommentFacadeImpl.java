package com.example.trainogram.facade.impl;

import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.*;
import org.springframework.stereotype.Component;

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
    public Comment addComment(Long postId, Comment comment) {
        User user = userService.findAuthenticatedUser();
        commentService.addComment(user, postId, comment);

        postService.addComment(postId, comment);
        notificationService.sendNotification(user,"commented your post" );

        return comment;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment commentExists = commentService.findCommentById(commentId);
        try {
            if (commentExists.getCommentAuthor().equals(userService.findAuthenticatedUser()) && commentExists.getPostId().equals(postId)) {
                postService.deleteComment(postId, commentId);
                commentService.deleteComment(commentId);
            }
        } catch (Exception e) {
            System.out.println("No authority to delete comment or no such comment exists");
        }
    }


    @Override
    public List<Comment> getAllComments(Long postId) {
        return commentService.getAllComments(postId);
    }

    @Override
    public void updateComment(Long postId, Long commentId, Comment comment) {
        Comment commentExists = commentService.findCommentById(commentId);
        try {
            if (commentExists.getCommentAuthor().equals(userService.findAuthenticatedUser()) && commentExists.getPostId().equals(postId)) {
                commentExists.setText(comment.getText());
                commentService.update(commentExists);
            }
        } catch (Exception e) {
            System.out.println("No authority to update comment or no such comment exists");
        }
    }
}
