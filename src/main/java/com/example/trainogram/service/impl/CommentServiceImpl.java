package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentRepository;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    //TODO TOKEN impl

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;


    private String link = "http://localhost:8080/users/post/";
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, PostService postService, NotificationService notificationService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
    }


    @Override
    public List<Comment> getAllComments(String token, Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public void createComment(String token, String commentText, Long postId) throws Status437PostNotFound {
        User user = userService.findAuthenticatedUser(token);
        Post post = postService.findPostById(postId);
        post.getComments().add(
                commentRepository.save(Comment.builder()
                .commentAuthor(user)
                .commentPub(LocalDateTime.now())
                .likes(0)
                .text(commentText)
                .post(post).build()));
        postService.updatePost(post);
        notificationService.sendNotification(post.getPostAuthor(), "User "+user.getUsername()+" commented your post",link+postId );
    }

    @Override
    public void deleteComment(String token, Long commentId) throws Status439CommentNotFound, Status435NoAuthorities {
        User authenticatedUser = userService.findAuthenticatedUser(token);
        Comment comment = findCommentById(commentId);
        if (authenticatedUser.equals(comment.getCommentAuthor()) || authenticatedUser.equals(comment.getPost().getPostAuthor()) ){
            commentRepository.deleteById(commentId);
        }
        else throw new Status435NoAuthorities("delete");
    }

    @Override
    public Comment findCommentById(Long commentId) throws Status439CommentNotFound {
        return commentRepository.findById(commentId).orElseThrow(()-> new Status439CommentNotFound(commentId));
    }

    @Override
    public void updateComment(String token, String commentText, Long commentId) throws Status439CommentNotFound {
        Comment comment = findCommentById(commentId);
        User user = userService.findAuthenticatedUser(token);
        if (comment.getCommentAuthor().equals(user)){
            if (commentText!=null && !Objects.equals(commentText, comment.getText())) {
                comment.setText(commentText);
                commentRepository.save(comment);
            }
        }

    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }
}
