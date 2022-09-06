package com.example.trainogram.controller;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.exception.UserException;
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

    //TODO: pictures to post required, saving / getting post image

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
        return postFacade.getPostPicture(id);
    }

    @PostMapping()
    public PostDto addPost(@RequestParam String postText,@RequestParam MultipartFile file) throws IOException {
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


    @GetMapping("/sponsorpost/{sponsorPostId}")
    public SponsorPostDto getSponsorPost(@PathVariable Long sponsorPostId){
        //find Post by PostId
        return postFacade.getSponsorPost(sponsorPostId);
    }

    @GetMapping("/sponsor/{sponsorId}")
    public List<SponsorPostDto> getAllSponsorPost(@PathVariable Long sponsorId){
        //search all post by Sponsor User
        return postFacade.getAllSponsorPost(sponsorId);     }

    @PostMapping("/sponsor")
    public SponsorPostDto addSponsorPost(@RequestParam String postText,@RequestParam MultipartFile file, Long sponsorId) throws IOException {
        return postFacade.addSponsorPost(postText,file, sponsorId);
    }

    @PutMapping("/sponsor/{id}")
    public SponsorPostDto updateSponsorPost(@PathVariable Long id, @RequestBody Post post){
        return postFacade.updateSponsorPost(id, post);
    }

    @DeleteMapping("/sponsor/{sponsorId}")
    public void deleteSponsorPost(@PathVariable Long sponsorId) throws UserException {
         postFacade.deleteSponsorPost(sponsorId);
    }

}
