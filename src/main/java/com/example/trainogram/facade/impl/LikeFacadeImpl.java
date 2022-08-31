package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.UserDto;
import com.example.trainogram.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class LikeFacadeImpl implements LikeFacade {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    private final ModelMapper mapToDto;

    private final NotificationService notificationService;

    public LikeFacadeImpl(LikeService likeService, UserService userService, PostService postService, ModelMapper mapToDto, NotificationService notificationService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
        this.mapToDto = mapToDto;
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
    public List<UserDto> findUserByPostId(Long postId) {
        List<User> users = likeService.findUserByPostId(postId);
        Type listType = new TypeToken<List<UserDto>>(){}.getType();
        return mapToDto.map(users,listType);

    }

    @Override
    public List<PostDto> findAllPostsLikedByUser() {
        User user = userService.findAuthenticatedUser();

        List<Post> posts = likeService.findAllPostsLikedByUser(user.getId());
        Type listType = new TypeToken<List<PostDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }
}
