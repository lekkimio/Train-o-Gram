package com.example.trainogram.facade;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostFacade {
    PostDto addPost(String postText, MultipartFile file) throws IOException;
    void deletePost(Long id) throws PostException;

    List<PostDto> findAllPosts();
    PostDto findByPostId(Long id);
    PostDto updatePost(Long id, Post post) throws PostException;

    /*void addComment(Long postId, Comment comment);

    void deleteComment(Long post, Long comment);
*/
    byte[] findPostPicture(Long id);
}
