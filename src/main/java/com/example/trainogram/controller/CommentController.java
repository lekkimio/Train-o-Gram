package com.example.trainogram.controller;


import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.dto.response.CommentResponseDto;
import com.example.trainogram.service.CommentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users/post/comment")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;


    public CommentController(CommentService commentFacade, ModelMapper modelMapper) {
        this.commentService = commentFacade;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{postId}")
    public List<CommentResponseDto> getAllComments(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable Long postId) {
        List<Comment> comments = commentService.getAllComments(token, postId);
        Type listType = new TypeToken<List<CommentResponseDto>>(){}.getType();
        return modelMapper.map(comments,listType);
    }

    @GetMapping("/get-comment/{commentId}")
    public CommentResponseDto getComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable Long commentId) throws Status439CommentNotFound {

        return modelMapper.map(commentService.getComment(token, commentId), CommentResponseDto.class);
    }

    @PostMapping("/{postId}")
    public void createComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                              @RequestParam String text, @PathVariable Long postId) throws Status437PostNotFound {
        commentService.createComment(token, text, postId);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                              @PathVariable Long commentId) throws Status435NoAuthorities, Status439CommentNotFound {
        commentService.deleteComment(token, commentId);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                              @RequestParam String commentText, @PathVariable Long commentId) throws Status439CommentNotFound {
        commentService.updateComment(token, commentText, commentId);
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
