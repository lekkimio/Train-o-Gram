package com.example.trainogram.service;

import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

import java.util.List;

public interface LikeService {

    void addLike(User user, Post post);

    void deleteLike(User user, Post post);

    List<Like> findAllLikes(Post post);
}
