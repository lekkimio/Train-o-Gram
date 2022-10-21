package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.*;
import com.example.trainogram.repository.LikeRepository;
import com.example.trainogram.repository.LikeToCommentRepository;
import com.example.trainogram.repository.LikeToPostRepository;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeToPostRepository likeToPostRepository;
    private final LikeToCommentRepository likeToCommentRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

//    public LikeService(LikeRepository likeRepository, PostService postService, CommentService commentService, UserService userService) {
//        this.likeRepository = likeRepository;
//        this.postService = postService;
//        this.commentService = commentService;
//        this.userService = userService;
//    }

    public List<User> findAllLikedUsersToPost(String token, Long postId) {
        return likeToPostRepository.findAllUsersToPost(postId);

    }

    public List<User> findAllLikedUsersToComment(String token, Long commentId) {
        userService.findUserByUsername(token);
        return likeToCommentRepository.findAllUsersToComment(commentId);
    }

    public List<Post> findAllLikedPostsByUser(String token) {
        User user = userService.findAuthenticatedUser(token);

        return likeToPostRepository.findAllPostsByUser(user);
    }

    public void addLikeToPost(String token, Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        User user = userService.findAuthenticatedUser(token);
        Like like = likeRepository.findByUser(user)
                .orElse(Like.builder().user(user).build());

        if(likeToPostRepository.findLikeByUserAndPost(user,post)==null) {
            likeToPostRepository.save(LikeToPost.builder()
                    .like(like)
                    .post(post)
                    .build());
            post.setLikes(post.getLikes()+1);
            postService.updatePost(post);
        }
    }

    public void deleteLikeFromPost(String token, Long postId) throws Status437PostNotFound {
        Post post = postService.findPostById(postId);
        User user = userService.findAuthenticatedUser(token);
        LikeToPost likeToPost = likeToPostRepository.findLikeByUserAndPost(user, post);

        if(likeToPost !=null) {
            likeToPostRepository.delete(likeToPost);
            post.setLikes(post.getLikes()-1);
            postService.updatePost(post);
        }
    }

    public void addLikeToComment(String token, Long commentId) throws Status439CommentNotFound {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser(token);
        Like like = likeRepository.findByUser(user)
                .orElse(Like.builder().user(user).build());

        if (likeToCommentRepository.findLikeByUserAndComment(user,comment)==null) {


            likeToCommentRepository.save(LikeToComment.builder()
                    .like(like)
                    .comment(comment)
                    .build());

            comment.setLikes(comment.getLikes()+1);
            commentService.updateComment(comment);

        }
    }

    public void deleteLikeFromComment(String token, Long commentId) throws Status439CommentNotFound, Status435NoAuthorities {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser(token);
        LikeToComment likeToComment = likeToCommentRepository.findLikeByUserAndComment(user, comment);
        if (likeToComment != null){
            likeToCommentRepository.delete(likeToComment);
            comment.setLikes(comment.getLikes()-1);
            commentService.updateComment(comment);
        }
    }
}
