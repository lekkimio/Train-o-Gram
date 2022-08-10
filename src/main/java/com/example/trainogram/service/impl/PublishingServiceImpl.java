package com.example.trainogram.service.impl;

import com.example.trainogram.model.Picture;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.PublishingService;

import java.time.LocalDateTime;

public class PublishingServiceImpl implements PublishingService {

    private final PostService postService;

    public PublishingServiceImpl(PostService postService) {
        this.postService = postService;
    }


    @Override
    public Post publishPost(User author, Picture picture, String text) {
        Post post = new Post();
        post.setPostAuthor(author);
        post.setPostPicture(picture);
        post.setPostText(text);
        post.setPubDate(LocalDateTime.now());
        return postService.addPost(post);
    }
}
