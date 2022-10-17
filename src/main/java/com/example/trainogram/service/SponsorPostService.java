package com.example.trainogram.service;


import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status438SponsorPostNotFound;
import com.example.trainogram.model.SponsorPost;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface SponsorPostService {

    SponsorPost createSponsorPost(String token, String postText, MultipartFile file, Long sponsorId) throws IOException, Status434UserNotFound;

    SponsorPost findSponsorPost(Long sponsorPostId) throws Status438SponsorPostNotFound;

    List<SponsorPost> findAllSponsorPost(Long sponsorId) throws Status434UserNotFound;

    void deleteSponsorPost(String token, Long sponsorId) throws Status438SponsorPostNotFound, Status435NoAuthorities;

    void updateSponsorPost(String token, Long sponsorPostId, String postText, MultipartFile file) throws IOException, Status438SponsorPostNotFound;

}
