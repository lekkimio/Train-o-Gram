package com.example.trainogram.service;


import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface SponsorPostService {

    SponsorPost addSponsorPost(Post post, User sponsor);

    void deleteSponsorPost(Long id);

    List<SponsorPost> findAllSponsorPostsByUser(Long id);

    SponsorPost findBySponsorPostId(Long id);


    SponsorPost updateSponsorPost(SponsorPost sponsorPost);
}
