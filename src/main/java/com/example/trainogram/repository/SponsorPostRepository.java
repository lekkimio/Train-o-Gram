package com.example.trainogram.repository;

import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorPostRepository extends JpaRepository<SponsorPost, Long> {

    List<SponsorPost> findAllByUserId(Long id);

    @Query("SELECT id from SponsorPost where id=(SELECT MAX(id) from SponsorPost)")
    Long max();

    SponsorPost findByPost(Post post);
}