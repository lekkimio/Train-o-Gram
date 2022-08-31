package com.example.trainogram.service.impl;

import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.repository.SponsorPostRepository;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SponsorPostServiceImpl implements SponsorPostService {

    private final SponsorPostRepository sponsorPostRepository;

    public SponsorPostServiceImpl(SponsorPostRepository sponsorPostRepository) {
        this.sponsorPostRepository = sponsorPostRepository;
    }


    @Override
    public SponsorPost addSponsorPost(Post post, Long sponsorId) {
        return null;
    }

    @Override
    public void deleteSponsorPost(Long id) {

    }

    @Override
    public List<SponsorPost> findAllSponsorPosts(Long id) {
        return null;
    }

    @Override
    public SponsorPost findBySponsorPostId(Long id) {
        return null;
    }

    @Override
    public SponsorPost updateSponsorPost(Long id, Post post) {
        return null;
    }
}
