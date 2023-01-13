package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.*;
import com.example.trainogram.repository.CommentLikeRepository;
import com.example.trainogram.repository.PostLikeRepository;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
@RequiredArgsConstructor
public class LikeService {
    
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    private final NotificationService notificationService;


    public List<User> findAllLikedUsersToPost(Long postId) {
        return postLikeRepository.findAllUsersToPost(postId);

    }

    public List<User> findAllLikedUsersToComment(String token, Long commentId) {
        userService.findUserByUsername(token);
        return commentLikeRepository.findAllUsersToComment(commentId);
    }

    public List<Post> findAllLikedPostsByUser(String token) {
        User user = userService.findAuthenticatedUser(token);

        return postLikeRepository.findAllPostsByUser(user);
    }

    public void addLikeToPost(String token, Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        User user = userService.findAuthenticatedUser(token);
        PostLike postLike = postLikeRepository.findLikeByUserAndPost(user,post);

        if(postLike==null) {
            postLikeRepository.save(PostLike.builder()
                    .post(post)
                    .user(user)
                    .build());
            String link = "http://localhost:8080/users/post/";
            notificationService.sendNotification(
                    post.getPostAuthor(),
                    "User "+user.getUsername()+" liked your post",
                    link +postId);
            postService.updatePost(post);
        }
    }

    public void deleteLikeFromPost(String token, Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        User user = userService.findAuthenticatedUser(token);
        PostLike postLike = postLikeRepository.findLikeByUserAndPost(user, post);

        if(postLike != null) {
            postLikeRepository.deleteById(postLike.getId());
        }
    }

    public void addLikeToComment(String token, Long commentId) throws Status439CommentNotFound {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser(token);
        CommentLike commentLike = commentLikeRepository.findLikeByUserAndComment(user, comment);

        if (commentLike == null) {
            commentLikeRepository.save(CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build());
            String link = "http://localhost:8080/users/post/comment/get-comment/";
            notificationService.sendNotification(
                    comment.getCommentAuthor(),
                    "User "+user.getUsername()+" liked your comment",
                    link+commentId);

        }

    }

    public void deleteLikeFromComment(String token, Long commentId) throws Status439CommentNotFound {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser(token);
        CommentLike commentLike = commentLikeRepository.findLikeByUserAndComment(user, comment);
        if (commentLike != null){
            commentLikeRepository.delete(commentLike);
        }
    }
}
