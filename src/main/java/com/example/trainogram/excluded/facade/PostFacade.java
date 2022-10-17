//package com.example.trainogram.facade;
//
//import com.example.trainogram.exception.CustomException;
//import com.example.trainogram.model.dto.response.PostResponseDto;
//import com.example.trainogram.model.dto.response.SponsorPostResponseDto;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//public interface PostFacade {
//    PostResponseDto createPost(String postText, MultipartFile file) throws IOException;
//    void deletePost(Long id) throws CustomException;
//
//    List<PostResponseDto> getAllUserPosts();
//    PostResponseDto getUserPostById(Long id);
//
//
//    byte[] getPostPicture(Long id);
//
//    void updatePost(Long id, String postText, MultipartFile newFile) throws IOException, CustomException;
//
//    SponsorPostResponseDto createSponsorPost(String postText, MultipartFile file, Long sponsorId) throws IOException, CustomException;
//
//    SponsorPostResponseDto getSponsorPost(Long sponsorPostId);
//
//    List<SponsorPostResponseDto> getAllSponsorPost(Long sponsorId);
//
//    void deleteSponsorPost(Long sponsorId) throws CustomException;
//
//    void updateSponsorPost(Long sponsorPostId, String postText, MultipartFile file) throws IOException;
//}
