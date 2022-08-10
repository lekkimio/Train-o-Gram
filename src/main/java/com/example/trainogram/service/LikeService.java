package com.example.trainogram.service;

import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

public interface LikeService {

    void addLike(User user, Post post);

    void deleteLike(User user, Post post);

}
