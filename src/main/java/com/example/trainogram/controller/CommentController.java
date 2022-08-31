package com.example.trainogram.controller;

import com.example.trainogram.exception.CommentException;
import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;

import com.example.trainogram.model.dto.CommentDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/posts/comments")
public class CommentController {

    private final CommentFacade commentFacade;

    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @GetMapping("/{postId}")
    public List<CommentDto> getAllComments(@PathVariable Long postId) {
        return commentFacade.getAllComments(postId);
    }

    @PostMapping("/{postId}")
    public CommentDto addComment(@RequestBody Comment comment, @PathVariable Long postId) {
        return commentFacade.addComment(postId,comment);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable Long postId,@PathVariable Long commentId) throws CommentException {
        commentFacade.deleteComment(postId,commentId);
    }

    @PutMapping("/{postId}/{commentId}")
    public void updateComment(@RequestBody Comment comment,@PathVariable Long postId ,@PathVariable Long commentId) throws CommentException {
        commentFacade.updateComment(postId,commentId,comment);
    }


}
