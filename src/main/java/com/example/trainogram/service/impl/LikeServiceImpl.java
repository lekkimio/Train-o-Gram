package com.example.trainogram.service.impl;

import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.LikeRepository;
import com.example.trainogram.service.LikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void addLike(User user, Post post) {
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
    }

    @Override
    public void deleteLike(User user, Post post) {
        Like like = likeRepository.getLikeByUserAndPost(user, post);
        System.out.println(like);
        likeRepository.delete(like);
    }

    @Override
    public List<Like> findAllLikes(Post post) {
        return likeRepository.findAllByPostId(post);
    }

    @Override
    public List<User> findUserByPostId(Long postId) {
        return likeRepository.findUserByPostId(postId);
    }

    @Override
    public List<Post> findAllPostsLikedByUser(Long userId) {
        return likeRepository.findAllPostsLikedByUser(userId);
    }

    @Override
    public Like findLikeByUserAndPost(User user, Post post) {
        return likeRepository.getLikeByUserAndPost(user, post);
    }
}
