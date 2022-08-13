package com.example.trainogram.controller;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping()
    public List<Post> getAllUserPost() {
        return postFacade.findAllPosts();
    }

    @GetMapping("/{id}")
    public Post getUserPost(@PathVariable Long id) {
        return postFacade.findByPostId(id);
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postFacade.addPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) throws PostException {
        return postFacade.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) throws PostException {
        postFacade.deletePost(id);
    }

}
