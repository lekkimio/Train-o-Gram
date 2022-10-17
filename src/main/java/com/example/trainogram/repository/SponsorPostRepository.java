package com.example.trainogram.repository;

import com.example.trainogram.model.SponsorPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorPostRepository extends JpaRepository<SponsorPost, Long> {

    List<SponsorPost> findAllByUserId(Long id);

    Optional<SponsorPost> findById(Long sponsorPostId);

    @Query("SELECT id from SponsorPost where id=(SELECT MAX(id) from SponsorPost)")
    Long max();
}