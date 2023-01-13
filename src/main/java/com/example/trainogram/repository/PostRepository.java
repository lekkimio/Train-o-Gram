package com.example.trainogram.repository;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.PostLike;
import com.example.trainogram.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.postAuthor.id = ?1")
    List<Post> findAllByUserId(Long id);


    @Query("SELECT id from Post where id=(SELECT MAX(id) from Post)")
    Long max();


    @Modifying
    @Transactional
    @Query("delete from SponsorPost p where p.post = ?1")
    void deleteIfExistsByPostAsSponsorPost(Post post);

    List<Post> findAllByPostAuthor(User user);



//   @Query(value = "select p from Post where  c")
//    Post findPostByComment(Comment comment);
}