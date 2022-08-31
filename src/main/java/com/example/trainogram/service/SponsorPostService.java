package com.example.trainogram.service;


import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface SponsorPostService {

    SponsorPost addSponsorPost(Post post, Long sponsorId);

    void deleteSponsorPost(Long id);

    List<SponsorPost> findAllSponsorPosts(Long id);

    SponsorPost findBySponsorPostId(Long id);

    SponsorPost updateSponsorPost(Long id, Post post);

}
