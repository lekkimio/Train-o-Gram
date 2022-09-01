package com.example.trainogram.repository;

import com.example.trainogram.model.SponsorPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorPostRepository extends JpaRepository<SponsorPost, Long> {

    List<SponsorPost> findAllByUserId(Long id);
}