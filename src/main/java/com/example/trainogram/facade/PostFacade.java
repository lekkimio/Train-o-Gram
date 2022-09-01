package com.example.trainogram.facade;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.exception.UserException;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.SponsorPostDto;
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

    SponsorPostDto addSponsorPost(String postText, MultipartFile file, Long sponsorId);

    SponsorPostDto getSponsorPost(Long sponsorPostId);

    List<SponsorPostDto> getAllSponsorPost(Long sponsorId);

    SponsorPostDto updateSponsorPost(Long id, Post post);

    void deleteSponsorPost(Long sponsorId) throws UserException;
}
