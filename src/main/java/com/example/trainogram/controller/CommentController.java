package com.example.trainogram.controller;

import com.example.trainogram.facade.CommentFacade;
import com.example.trainogram.model.Comment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentFacade commentFacade;

    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @GetMapping("/{postId}")
    public List<Comment> getAllComments(@PathVariable Long postId) {
        return commentFacade.getAllComments(postId);
    }

    @PostMapping("/{postId}")
    public Comment addComment(@RequestBody Comment comment,@PathVariable Long postId) {
        return commentFacade.addComment(postId,comment);
    }

    @PutMapping("/{postId}/{commentId}")
    public void updateComment(@RequestBody Comment comment,@PathVariable Long postId ,@PathVariable Long commentId) {
        commentFacade.updateComment(postId,commentId,comment);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable Long postId,@PathVariable Long commentId) {
        commentFacade.deleteComment(postId,commentId);
    }

}
