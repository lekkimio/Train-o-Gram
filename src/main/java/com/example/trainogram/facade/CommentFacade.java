package com.example.trainogram.facade;

import com.example.trainogram.exception.CommentException;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.dto.CommentDto;

import java.util.List;

public interface CommentFacade {

    /*Comment addComment(Long postId, Comment comment);

    void deleteComment(Long postId, Long commentId);*/

    List<CommentDto> getAllComments(Long postId);

    void updateComment(Long postId, Long commentId, Comment comment) throws CommentException;

    CommentDto addComment(Long postId, Comment comment);

    void deleteComment(Long postId, Long commentId) throws CommentException;
}
