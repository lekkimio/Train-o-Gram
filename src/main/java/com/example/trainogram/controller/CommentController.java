package com.example.trainogram.controller;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.CommentFacade;

import com.example.trainogram.model.dto.response.CommentResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/post/comment")
public class CommentController {

    private final CommentFacade commentFacade;


    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @GetMapping("/{postId}")
    public List<CommentResponseDto> getAllComments(@PathVariable Long postId) throws CustomException {
        return commentFacade.getAllComments(postId);
    }

    @PostMapping("/{postId}")
    public void createComment(@RequestParam String commentText, @PathVariable Long postId) {
        commentFacade.createComment(commentText, postId);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) throws CustomException {
        commentFacade.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@RequestParam String commentText, @PathVariable Long commentId) throws CustomException {
        commentFacade.updateComment(commentText, commentId);
    }


     /*@PostMapping("/{postId}")
    public void createComment(@RequestBody String commentText, @PathVariable Long postId) {
        commentFacade.addComment(postId,commentText);
    }*/

    /*@DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable Long postId,@PathVariable Long commentId) throws CommentException {
        commentFacade.deleteComment(postId,commentId);
    }*/

    /*@PutMapping("/{postId}/{commentId}")
    public void updateComment(@RequestBody Comment comment,@PathVariable Long postId ,@PathVariable Long commentId) throws CommentException {
        commentFacade.updateComment(postId,commentId,comment);
    }*/

}
