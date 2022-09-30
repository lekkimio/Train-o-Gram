package com.example.trainogram.service.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.LikeToComment;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.LikeToCommentRepository;
import com.example.trainogram.service.LikeToCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeToCommentServiceImpl implements LikeToCommentService {

    private final LikeToCommentRepository likeToCommentRepository;

    public LikeToCommentServiceImpl(LikeToCommentRepository likeToCommentRepository) {
        this.likeToCommentRepository = likeToCommentRepository;
    }

    @Override
    public List<User> findAllLikedUsersToComment(Long commentId) {
        return likeToCommentRepository.findAllUsersToComment(commentId);
    }

    @Override
    public void deleteLikeFromComment(Comment comment, User user) {
        LikeToComment likeToComment = likeToCommentRepository.findLikeToPostByCommentAndUser(comment,user);
        if (likeToComment!=null)
            likeToCommentRepository.deleteById(likeToComment.getId());
    }

    @Override
    public void addLikeToComment(Comment comment, User user) throws CustomException {
        if (likeToCommentRepository.findLikeToPostByCommentAndUser(comment, user) == null) {
            likeToCommentRepository.save(LikeToComment.builder()
                    .comment(comment)
                    .user(user)
                    .build());
        }
        else {
            throw new CustomException("You have already liked this comment", HttpStatus.BAD_REQUEST);
        }
    }

}
