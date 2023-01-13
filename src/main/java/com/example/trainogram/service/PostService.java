package com.example.trainogram.service;


import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PostService {
    Post createPost(String token, String postText, List<MultipartFile> file) throws IOException;

    void deletePost(String token, Long id) throws Status437PostNotFound, IOException, Status435NoAuthorities;

    List<Post> findAllPostsByUser(String token);

    List<Post> findAllPostsByUser(Long id) throws Status434UserNotFound;

    Post findPostById(Long id) throws Status437PostNotFound;

//    byte[] getPostPicture(String token, Long id) throws Status437PostNotFound, IOException;

//    List<InputStream> getPostPicture(String token, Long id) throws Status437PostNotFound, IOException;

    void updatePost(String token, Long id, String postText, List<MultipartFile> file) throws IOException, Status437PostNotFound, Status435NoAuthorities;

    void updatePost(Post post);

//    Post findPostByComment(Comment comment);
}
