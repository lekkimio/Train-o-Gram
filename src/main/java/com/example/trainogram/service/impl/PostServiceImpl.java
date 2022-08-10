package com.example.trainogram.service.impl;

import com.example.trainogram.model.Post;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findAllPosts(Long id) {
        return postRepository.findAllByUserId(id);
    }

    @Override
    public Post findByPostId(Long id) {
        return postRepository.findByPostId(id);
    }
}
