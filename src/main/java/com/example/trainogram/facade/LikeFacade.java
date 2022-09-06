package com.example.trainogram.facade;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.model.Like;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.UserDto;

import java.util.List;

public interface LikeFacade {

    void addLikeToPost(Long postId) throws LikeException;

    void deleteLikeFromPost(Long postId);

    List<Like> findAllLikedUsers(Long postId);

    List<UserDto> findUserByPostId(Long postId);

    List<PostDto> findAllPostsLikedByUser();

    void deleteLikeFromComment(Long commentId);

    void addLikeToComment(Long commentId);
}
