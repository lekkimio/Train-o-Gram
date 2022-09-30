package com.example.trainogram.service;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeToCommentService {
    List<User> findAllLikedUsersToComment(Long commentId);

    void deleteLikeFromComment(Comment comment, User user);

    void addLikeToComment(Comment comment, User user) throws CustomException;
}
