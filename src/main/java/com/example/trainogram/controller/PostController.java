package com.example.trainogram.controller;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<Post> getAllUserPost(User user) {
        return postService.findAllPosts(user.getId());
    }

    @GetMapping("/{id}")
    public Post getUserPost(@PathVariable Long id) {
        return postService.findByPostId(id);
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    /*@PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) throws UserNotFoundException {
        return postService.updatePost(id, post);
    }*/

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, Principal principal) {

        postService.deletePost(id);
    }

}
