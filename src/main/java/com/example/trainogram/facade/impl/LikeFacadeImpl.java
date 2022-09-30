package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class LikeFacadeImpl implements LikeFacade {

    private final LikeToCommentService likeToCommentService;
    private final LikeToPostService likeToPostService;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final ModelMapper mapToDto;

    public LikeFacadeImpl(LikeToCommentService likeToCommentService, LikeToPostService likeToPostService, UserService userService, PostService postService, CommentService commentService, NotificationService notificationService, ModelMapper mapToDto) {
        this.likeToCommentService = likeToCommentService;
        this.likeToPostService = likeToPostService;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.notificationService = notificationService;
        this.mapToDto = mapToDto;
    }


    @Override
    public List<PostResponseDto> findAllLikedPostsByUser() {
        User user = userService.findAuthenticatedUser();
        List<Post> posts = likeToPostService.findAllLikedPostsByUser(user);
        Type listType = new TypeToken<List<PostResponseDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }

    @Override
    public List<UserResponseDto> findAllLikedUsersToPost(Long postId) {
        List<User> users = likeToPostService.findAllLikedUsersToPost(postId);
        Type listType = new TypeToken<List<UserResponseDto>>(){}.getType();
        return mapToDto.map(users,listType);
    }

    @Override
    public List<UserResponseDto> findAllLikedUsersToComment(Long commentId) {
        List<User> users = likeToCommentService.findAllLikedUsersToComment(commentId);
        Type listType = new TypeToken<List<UserResponseDto>>(){}.getType();
        return mapToDto.map(users,listType);
    }

    @Override
    public void addLikeToPost(Long postId) throws CustomException {
         Post post = postService.findByPostId(postId);
         User user = userService.findAuthenticatedUser();
         likeToPostService.addLikeToPost(post, user);
            post.setLikes(post.getLikes()+1);
            postService.updatePostLikeCount(post);
            notificationService.sendNotification(post.getPostAuthor(), "User "+user.getUsername()+" liked your post");
    }

    @Override
    public void deleteLikeFromPost(Long postId) {
        Post post = postService.findByPostId(postId);
        User user = userService.findAuthenticatedUser();
        likeToPostService.deleteLikeFromPost(post, user);
        int likeCount = post.getLikes();
        if (!(likeCount-1 < 0)) {
            post.setLikes(post.getLikes()-1);
        }
        postService.updatePostLikeCount(post);
    }

    @Override
    public void addLikeToComment(Long commentId) throws CustomException {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser();
        likeToCommentService.addLikeToComment(comment, user);
        comment.setLikes(comment.getLikes()+1);
        commentService.updateCommentLikeCount(comment);

        notificationService.sendNotification(comment.getCommentAuthor(), "User "+user.getUsername()+" liked your comment");
    }

    @Override
    public void deleteLikeFromComment(Long commentId) {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findAuthenticatedUser();
        likeToCommentService.deleteLikeFromComment(comment, user);
        int likeCount = comment.getLikes();
        if (!(likeCount-1 < 0)) {
            comment.setLikes(comment.getLikes()-1);
        }
        commentService.updateCommentLikeCount(comment);

    }
}
