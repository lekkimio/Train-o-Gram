package com.example.trainogram.repository;

import com.example.trainogram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.postAuthor.id = ?1")
    List<Post> findAllByUserId(Long id);

    @Query("SELECT p FROM Post p WHERE p.postAuthor.id = ?1 AND p.id = ?2")
    Post findByPostId(Long id);
}