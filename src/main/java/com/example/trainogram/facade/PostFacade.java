package com.example.trainogram.facade;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.SponsorPostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostFacade {
    PostResponseDto createPost(String postText, MultipartFile file) throws IOException;
    void deletePost(Long id) throws CustomException;

    List<PostResponseDto> getAllUserPosts();
    PostResponseDto getUserPostById(Long id);

    /*PostResponseDto updatePost(Long id, Post post) throws PostException;

    void addComment(Long postId, Comment comment);

    void deleteComment(Long post, Long comment);
    */

    SponsorPostResponseDto createSponsorPost(String postText, MultipartFile file, Long sponsorId) throws IOException, CustomException;

    SponsorPostResponseDto getSponsorPost(Long sponsorPostId);

    List<SponsorPostResponseDto> getAllSponsorPost(Long sponsorId);

//    void updateSponsorPost(Long id, Post post);

    void deleteSponsorPost(Long sponsorId) throws CustomException;

    byte[] getPostPicture(Long id);

    void updatePost(Long id, String postText, MultipartFile newFile) throws IOException, CustomException;

    void updateSponsorPost(Long sponsorPostId, String postText, MultipartFile file) throws IOException;
}
