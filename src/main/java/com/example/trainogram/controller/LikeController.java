package com.example.trainogram.controller;


import com.example.trainogram.facade.LikeFacade;
import com.example.trainogram.model.Like;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeFacade likeFacade;

    public LikeController(LikeFacade likeFacade) {
        this.likeFacade = likeFacade;
    }

    @GetMapping("/{postId}")
    public List<Like> getAllLikedUsers(@PathVariable Long postId) {
        return likeFacade.findAllLikedUsers(postId);
    }

    @PostMapping("/{postId}")
    public void addLikeToPost(@PathVariable Long postId) {
        likeFacade.addLikeToPost(postId);
    }

    @DeleteMapping("/{postId}")
    public void deleteLikeFromPost(@PathVariable Long postId) {
        likeFacade.deleteLikeFromPost(postId);
    }

}
