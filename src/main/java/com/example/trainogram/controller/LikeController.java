package com.example.trainogram.controller;


import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class LikeController {

    private final LikeFacade likeFacade;

    public LikeController(LikeFacade likeFacade) {
        this.likeFacade = likeFacade;
    }

   /* @GetMapping("/{postId}")
    public List<Like> getAllLikedUsers(@PathVariable Long postId) {
        return likeFacade.findAllLikedUsers(postId);
    }*/

    @GetMapping("/post/like/{postId}")
    public List<UserResponseDto> getAllLikedUsersToPost(@PathVariable Long postId) {
        return likeFacade.findAllLikedUsersToPost(postId);

    }

    @GetMapping("/post/like")
    public List<PostResponseDto> getAllLikedPostsByUser() {
        return likeFacade.findAllLikedPostsByUser();
    }

    @GetMapping("/comment/like/{commentId}")
    public List<UserResponseDto> getAllLikedUsersToComment(@PathVariable Long commentId) {
        return likeFacade.findAllLikedUsersToComment(commentId);

    }

    @PostMapping("/post/like/{postId}")
    public void addLikeToPost(@PathVariable Long postId) throws CustomException {
        likeFacade.addLikeToPost(postId);

    }

    @DeleteMapping("/post/like/{postId}")
    public void deleteLikeFromPost(@PathVariable Long postId) {
        likeFacade.deleteLikeFromPost(postId);
    }

    @PostMapping("/comment/like/{commentId}")
    public void addLikeToComment(@PathVariable Long commentId) throws CustomException {
        likeFacade.addLikeToComment(commentId);
    }

    @DeleteMapping("/comment/like/{commentId}")
    public void deleteLikeFromComment(@PathVariable Long commentId) {
        likeFacade.deleteLikeFromComment(commentId);
    }
}
