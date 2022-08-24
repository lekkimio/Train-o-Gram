package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikeFacadeImpl implements LikeFacade {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    private final NotificationService notificationService;

    public LikeFacadeImpl(LikeService likeService, UserService userService, PostService postService, NotificationService notificationService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    /*public void likePost(Post post) {
        User user = userService.findAuthenticatedUser();
        likeService.addLike(user, post);
        notificationService.sendNotification
                (post.getPostAuthor(),
                        "user "+ user.getUsername() +" liked your post");
    }*/

    @Override
    public void addLikeToPost(Long postId) throws LikeException {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        Like like = likeService.findLikeByUserAndPost(user, post);
        if(!like.getUser().getId().equals(user.getId())) {
            post.setLikes(post.getLikes()+1);
            likeService.addLike(user, post);
            postService.updatePost(postId, post);
            notificationService.sendNotification
                    (post.getPostAuthor(),
                            "user "+ user.getUsername() +" liked your post");
        }else {
            throw new LikeException("You already liked this post");
        }

    }

    @Override
    public void deleteLikeFromPost(Long postId) {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(postId);
        post.setLikes(post.getLikes()- 1);
        postService.updatePost(postId, post);
        likeService.deleteLike(user, post);
    }

    @Override
    public List<Like> findAllLikedUsers(Long postId) {
        Post post = postService.findByPostId(postId);
        return likeService.findAllLikes(post);
    }

    @Override
    public List<User> findUserByPostId(Long postId) {
        return likeService.findUserByPostId(postId);
    }

    @Override
    public List<Post> findAllPostsLikedByUser() {
        User user = userService.findAuthenticatedUser();
        return likeService.findAllPostsLikedByUser(user.getId());
    }
}
