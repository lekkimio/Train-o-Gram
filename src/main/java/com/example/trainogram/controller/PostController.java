package com.example.trainogram.controller;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.SponsorPostDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users/posts")
public class PostController {

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping()
    public List<PostDto> getAllUserPost() {
        return postFacade.findAllPosts();
    }

    @GetMapping("/{id}")
    public PostDto getUserPost(@PathVariable Long id) {
        return postFacade.findByPostId(id);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getPostPicture(@PathVariable Long id) {
        return postFacade.findPostPicture(id);
    }

    @PostMapping()
    public PostDto addPost(@RequestParam String postText, MultipartFile file) throws IOException {
        return postFacade.addPost(postText, file);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody Post post) throws PostException {
        return postFacade.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) throws PostException {
        postFacade.deletePost(id);
    }


    /**
    sponsor posts
    **/


    @GetMapping("/sponsor/{sponsorPostId}")
    public SponsorPostDto getSponsorPost(@PathVariable Long sponsorPostId){
        //find Post
        return postFacade.getSponsorPost(sponsorPostId);
    }

    @GetMapping("/sponsor/{sponsorId}")
    public List<SponsorPostDto> getAllSponsorPost(@PathVariable Long sponsorId){
        //search by User
        return postFacade.getAllSponsorPost(sponsorId);
    }

    @PostMapping("/sponsor")
    public SponsorPostDto addSponsorPost(@RequestParam String postText, MultipartFile file, Long sponsorId){
        return postFacade.addSponsorPost(postText,file, sponsorId);
    }

    @PutMapping("/sponsor/{sponsorId}")
    public SponsorPostDto updateSponsorPost(@PathVariable Long sponsorId){
        return postFacade.updateSponsorPost(sponsorId);
    }

    @DeleteMapping("/sponsor/{sponsorId}")
    public SponsorPostDto deleteSponsorPost(@PathVariable Long sponsorId){
        return postFacade.deleteSponsorPost(sponsorId);
    }

}
