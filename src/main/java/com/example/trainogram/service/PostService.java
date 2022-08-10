package com.example.trainogram.service;


import com.example.trainogram.model.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostService {

    Post addPost(Post post);

    void deletePost(Long id);
    List<Post> findAllPosts(Long id);

    Post findByPostId(Long id);
}
