package com.example.trainogram.controller;


import com.example.trainogram.exception.LikeException;
import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/posts/like")
public class LikeController {

    private final LikeFacade likeFacade;

    public LikeController(LikeFacade likeFacade) {
        this.likeFacade = likeFacade;
    }

   /* @GetMapping("/{postId}")
    public List<Like> getAllLikedUsers(@PathVariable Long postId) {
        return likeFacade.findAllLikedUsers(postId);
    }*/

    @GetMapping("/{postId}")
    public List<UserDto> getAllLikedUsers(@PathVariable Long postId) {
        return likeFacade.findUserByPostId(postId);

    }

    @GetMapping()
    public List<PostDto> getAllLikedPosts() {
        return likeFacade.findAllPostsLikedByUser();
    }

    @PostMapping("/post/{postId}")
    public void addLikeToPost(@PathVariable Long postId) throws LikeException {
        likeFacade.addLikeToPost(postId);
    }

    @DeleteMapping("/post/{postId}")
    public void deleteLikeFromPost(@PathVariable Long postId) {
        likeFacade.deleteLikeFromPost(postId);
    }

    @PostMapping("/comment/{commentId}")
    public void addLikeToComment(@PathVariable Long commentId) throws LikeException {
        likeFacade.addLikeToComment(commentId);
    }

    @DeleteMapping("/comment/{commentId}")
    public void deleteLikeFromComment(@PathVariable Long commentId) {
        likeFacade.deleteLikeFromComment(commentId);
    }
}
