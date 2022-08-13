package com.example.trainogram.facade;

import com.example.trainogram.model.Like;

import java.util.List;

public interface LikeFacade {

    void addLikeToPost(Long postId);

    void deleteLikeFromPost(Long postId);

    List<Like> findAllLikedUsers(Long postId);
}
