package com.example.trainogram.facade;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

import java.util.List;

public interface LikeFacade {

    void addLikeToPost(Long postId) throws LikeException;

    void deleteLikeFromPost(Long postId);

    List<Like> findAllLikedUsers(Long postId);

    List<User> findUserByPostId(Long postId);

    List<Post> findAllPostsLikedByUser();
}
