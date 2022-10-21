package com.example.trainogram.repository;

import com.example.trainogram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.postAuthor.id = ?1")
    List<Post> findAllByUserId(Long id);

//    @Query("SELECT p FROM Post p WHERE p.id = ?1")
//    Post findByPostId(Long id);

    @Query("SELECT id from Post where id=(SELECT MAX(id) from Post)")
    Long max();


    @Modifying
    @Transactional
    @Query("delete from SponsorPost p where p.post = ?1")
    void deleteIfExistsByPostAsSponsorPost(Post post);
}