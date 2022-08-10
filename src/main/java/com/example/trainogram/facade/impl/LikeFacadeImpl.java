package com.example.trainogram.facade.impl;

import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.LikeService;
import com.example.trainogram.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class LikeFacadeImpl implements LikeFacade {

    private final LikeService likeService;
    private final NotificationService notificationService;

    public LikeFacadeImpl(LikeService likeService, NotificationService notificationService) {
        this.likeService = likeService;
        this.notificationService = notificationService;
    }

    public void likePost(Post post, User user) {
        likeService.addLike(user, post);
        notificationService.sendNotification
                (post.getPostAuthor(),
                        "user "+ user.getUsername() +" liked your post");
    }
}
