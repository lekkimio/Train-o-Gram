package com.example.trainogram.facade;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;

import java.util.List;

public interface LikeFacade {
    List<UserResponseDto> findAllLikedUsersToComment(Long commentId);

    List<UserResponseDto> findAllLikedUsersToPost(Long postId);

    List<PostResponseDto> findAllLikedPostsByUser();


    void addLikeToPost(Long postId) throws CustomException;

    void deleteLikeFromPost(Long postId);

    void addLikeToComment(Long commentId) throws CustomException;

    void deleteLikeFromComment(Long commentId);
}
