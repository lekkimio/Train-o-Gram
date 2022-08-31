package com.example.trainogram.repository;

import com.example.trainogram.model.SponsorPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorPostRepository extends JpaRepository<SponsorPost, Long> {
}