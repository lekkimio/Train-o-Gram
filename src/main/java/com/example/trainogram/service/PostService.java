package com.example.trainogram.service;


import com.example.trainogram.model.Post;

import java.util.List;

public interface PostService {

    Post addPost(Post post);

    void deletePost(Long id);
    List<Post> findAllPosts(Long id);

    Post findByPostId(Long id);

//    Post updatePost(Long id, Post post);

    void updatePostLikeCount(Post post);

    void updatePost(Post post);
}
