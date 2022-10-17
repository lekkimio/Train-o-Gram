package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.LikeRepository;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    public LikeService(LikeRepository likeRepository, PostService postService, CommentService commentService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    public List<User> findAllLikedUsersToPost(String token, Long postId) {
        return likeRepository.findAllUsersToPost(postId);

    }

    public List<User> findAllLikedUsersToComment(String token, Long commentId) {
        userService.findUserByUsername(token);
        return likeRepository.findAllUsersToComment(commentId);
    }

    public List<Post> findAllLikedPostsByUser(String token) {
        User user = userService.findAuthenticatedUser(token);

        return likeRepository.findAllPostsByUser(user);
    }

    public void addLikeToPost(Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        post.setLikes(post.getLikes()+1);
        postService.updatePost(post);
    }

    public void deleteLikeFromPost(Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        post.setLikes(post.getLikes()-1);
        postService.updatePost(post);
    }

    public void addLikeToComment( Long commentId) throws Status439CommentNotFound {
        Comment comment = commentService.findCommentById(commentId);
        comment.setLikes(comment.getLikes()+1);
        commentService.updateCommentLikeCount(comment);
    }

    public void deleteLikeFromComment(Long commentId) throws Status439CommentNotFound, Status435NoAuthorities {
        Comment comment = commentService.findCommentById(commentId);
        comment.setLikes(comment.getLikes()-1);
        commentService.updateCommentLikeCount(comment);
    }
}
