package com.example.trainogram.facade;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentFacade {

    /*Comment addComment(Long postId, Comment comment);

    void deleteComment(Long postId, Long commentId);*/

    List<CommentResponseDto> getAllComments(Long postId) throws CustomException;

    /*void updateComment(Long postId, Long commentId, Comment comment) throws CommentException;

    void addComment(Long postId, String commentText);

    void deleteComment(Long postId, Long commentId) throws CommentException;*/

    void createComment(String commentText, Long postId);

    void deleteComment(Long commentId) throws CustomException;

    void updateComment(String comment, Long commentId) throws CustomException;
}
