package com.example.trainogram.repository;

import com.example.trainogram.model.PostLike;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select l from PostLike l where l.post = ?2 and l.user =?1")
    PostLike findLikeByUserAndPost(User user, Post post);

    @Query("select p from PostLike l LEFT JOIN FETCH Post p on l.post.id = p.id where l.user = ?1")
    List<Post> findAllPostsByUser(User user);

    @Query("select u from PostLike l LEFT JOIN FETCH User u on l.user.id = u.id where l.post.id = ?1")
    List<User> findAllUsersToPost(Long postId);

    List<PostLike> findAllByPostId(Long postId);

    @Modifying
    @Transactional
    @Query("delete from PostLike p where p.post=?1 and p.user=?2")
    void deleteByLike(Post post, User user);
}
