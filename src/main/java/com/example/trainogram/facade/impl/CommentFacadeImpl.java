package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CommentException;
import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.CommentDto;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService commentService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final LikeService likeService;
    private final UserService userService;
    private  final ModelMapper mapToDto;


    public CommentFacadeImpl(CommentService commentService, PostService postService, NotificationService notificationService, LikeService likeService, UserService userService, ModelMapper mapToDto) {
        this.commentService = commentService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.likeService = likeService;
        this.userService = userService;
        this.mapToDto = mapToDto;
    }


    @Override
    public List<CommentDto> getAllComments(Long postId) {

        List<Comment> comments = commentService.getAllComments(postId);
        Type listType = new TypeToken<List<CommentDto>>(){}.getType();
        return mapToDto.map(comments,listType);
    }

    @Override
    public void updateComment(Long postId, Long commentId, Comment comment) throws CommentException {
        Comment commentExists = commentService.findCommentById(commentId);
            if (commentExists.getCommentAuthor().equals(userService.findAuthenticatedUser())
                    && commentExists.getPost().getId().equals(postId)) {
                commentExists.setText(comment.getText());
                commentService.update(commentExists);
            } else {
                throw new CommentException("You can't update this comment");
            }

    }

    @Override
    public CommentDto addComment(Long postId, Comment comment) {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        comment.setCommentAuthor(user);
        comment.setPost(post);
        Comment savedComment = commentService.addComment(comment);
        post.getComments().add(savedComment);
        postService.updatePost(postId, post);

        return mapToDto.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) throws CommentException {
        Comment comment = commentService.findCommentById(commentId);
        if (comment.getCommentAuthor().equals(userService.findAuthenticatedUser())
                && comment.getPost().getId().equals(postId)) {
            commentService.deleteComment(commentId);
        } else {
            throw new CommentException("You can't delete this comment");
        }

    }
}
