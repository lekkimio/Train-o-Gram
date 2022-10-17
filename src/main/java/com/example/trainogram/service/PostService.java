package com.example.trainogram.service;


import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post createPost(String token, String postText, MultipartFile file) throws IOException;

    void deletePost(String token, Long id) throws Status437PostNotFound, IOException, Status435NoAuthorities;

    List<Post> findAllPostsByUser(String token);
    Post findPostById(Long id) throws Status437PostNotFound;

    byte[] getPostPicture(String token, Long id) throws Status437PostNotFound;

    void updatePost(String token, Long id, String postText, MultipartFile file) throws IOException, Status437PostNotFound, Status435NoAuthorities;

    void updatePost(Post post);
}
