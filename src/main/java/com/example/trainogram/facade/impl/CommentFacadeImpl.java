package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

import com.example.trainogram.model.dto.response.CommentResponseDto;
import com.example.trainogram.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService commentService;
    private final PostService postService;
    private final NotificationService notificationService;
    private final UserService userService;
    private  final ModelMapper mapToDto;


    public CommentFacadeImpl(CommentService commentService, PostService postService,
                             NotificationService notificationService,
                             UserService userService, ModelMapper mapToDto) {
        this.commentService = commentService;
        this.postService = postService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.mapToDto = mapToDto;
    }


    @Override
    public List<CommentResponseDto> getAllComments(Long postId) throws CustomException {

        if (postService.findByPostId(postId)==null){
            throw new CustomException("Provide correct Comment Id", HttpStatus.BAD_REQUEST);
        }

        List<Comment> comments = commentService.getAllComments(postId);
        Type listType = new TypeToken<List<CommentResponseDto>>(){}.getType();
        return mapToDto.map(comments,listType);
    }



    @Override
    public void createComment(String commentText, Long postId) {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        post.getComments().add(
                commentService.
                        addComment(
                                Comment.builder()
                                        .text(commentText)
                                        .post(post)
                                        .commentAuthor(user)
                                        .likes(0)
                                        .build()));
        postService.updatePost(post);
        notificationService.sendNotification(post.getPostAuthor(), "User "+user.getUsername()+" commented your post");


    }


    @Override
    public void deleteComment(Long commentId) throws CustomException {
        User user = userService.findAuthenticatedUser();
        Comment comment = commentService.findCommentById(commentId);
        if (user.equals(comment.getCommentAuthor())) {
           commentService.deleteComment(commentId);
        }
        else {
            throw new CustomException("No authorities to delete comment", HttpStatus.UNAUTHORIZED);
        }
        //exception
    }

    @Override
    public void updateComment(String commentText, Long commentId) throws CustomException {
        User user = userService.findAuthenticatedUser();
        Comment commentToUpdate = commentService.findCommentById(commentId);

        if (commentToUpdate.getCommentAuthor().equals(user)){
            if (commentText != null) {
                commentToUpdate.setText(commentText);
            }
            commentService.updateComment(commentToUpdate);
        }else {
            throw new CustomException("No authorities to update comment", HttpStatus.UNAUTHORIZED);
        }

        //exception
    }


    /*  @Override
    public void updateComment(Long postId, Long commentId, Comment comment) throws CommentException {
        Comment commentExists = commentService.findCommentById(commentId);
            if (commentExists.getCommentAuthor().equals(userService.findAuthenticatedUser())
                    && commentExists.getPost().getId().equals(postId)) {
                commentExists.setText(comment.getText());
                commentService.update(commentExists);
            } else {
                throw new CommentException("You can't update this comment");
            }

    }*/

   /* @Override
    public void addComment(Long postId, String commentText) {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        post.getComments().add(
                commentService.
                        addComment(
                                Comment.builder()
                                        .text(commentText)
                                        .post(post)
                                        .commentAuthor(user)
                                        .build()));
        postService.updatePost(postId, post);
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

    }*/
}
