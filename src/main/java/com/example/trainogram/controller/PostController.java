package com.example.trainogram.controller;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Post;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getPostPicture(@PathVariable Long id) {
        return postFacade.findPostPicture(id);
    }

    @PostMapping()
    public Post addPost(@RequestParam String postText, MultipartFile file) throws IOException {
        return postFacade.addPost(postText, file);
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
