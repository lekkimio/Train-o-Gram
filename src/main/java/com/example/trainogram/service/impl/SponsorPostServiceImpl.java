package com.example.trainogram.service.impl;

import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.SponsorPostRepository;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import net.bytebuddy.asm.Advice;
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
    public SponsorPost addSponsorPost(Post post, User sponsor) {
        SponsorPost sponsorPost = new SponsorPost();
        sponsorPost.setPost(post);
        sponsorPost.setUser(sponsor);
        return sponsorPostRepository.save(sponsorPost);
    }

    @Override
    public void deleteSponsorPost(Long id) {
        sponsorPostRepository.deleteById(id);
    }

    @Override
    public List<SponsorPost> findAllSponsorPostsByUser(Long id) {
        return sponsorPostRepository.findAllByUserId(id);
    }

    @Override
    public SponsorPost findBySponsorPostId(Long id) {
        return sponsorPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No post with this id"));
    }

    @Override
    public SponsorPost updateSponsorPost(SponsorPost sponsorPost) {
        return sponsorPostRepository.save(sponsorPost);
    }
}
