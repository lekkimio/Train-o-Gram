package com.example.trainogram.facade;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.model.Post;

import java.util.List;

public interface PostFacade {
    Post addPost(Post post);
    void deletePost(Long id) throws PostException;

    List<Post> findAllPosts();
    Post findByPostId(Long id);
    Post updatePost(Long id, Post post) throws PostException;
}
