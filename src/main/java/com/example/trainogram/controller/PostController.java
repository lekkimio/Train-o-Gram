package com.example.trainogram.controller;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.SponsorPostResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users/post")
public class PostController {


    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping()
    public List<PostResponseDto> getAllUserPost() {
        return postFacade.getAllUserPosts();
    }

    @GetMapping("/{id}")
    public PostResponseDto getUserPost(@PathVariable Long id) {
        return postFacade.getUserPostById(id);
    }


    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getPostPicture(@PathVariable Long id) {
        return postFacade.getPostPicture(id);
    }

    @PostMapping()
    public void createPost(@RequestParam String postText,
                           @RequestParam MultipartFile file) throws IOException {
        postFacade.createPost(postText, file);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) throws CustomException {
        postFacade.deletePost(id);
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestParam(required = false) String postText,
                           @RequestParam(required = false) MultipartFile file,
                           @PathVariable Long id) throws IOException, CustomException {
        postFacade.updatePost(id, postText, file);
    }

    /**
    sponsor posts
    **/

    @GetMapping("/sponsor/{sponsorPostId}")
    public SponsorPostResponseDto getSponsorPost(@PathVariable Long sponsorPostId){
        //find Post by SponsorPostId
        return postFacade.getSponsorPost(sponsorPostId);
    }



    @PostMapping("/sponsor")
    public void createSponsorPost(@RequestParam String postText,
                                  @RequestParam Long sponsorId,
                                  @RequestParam MultipartFile file) throws IOException, CustomException{
        postFacade.createSponsorPost(postText,file, sponsorId);
    }

    @PutMapping("/sponsor/{sponsorPostId}")
    public void updateSponsorPost(@PathVariable Long sponsorPostId,
                                  @RequestParam String postText,
                                  @RequestParam MultipartFile file) throws IOException {
        postFacade.updateSponsorPost(sponsorPostId, postText, file);
    }

    @DeleteMapping("/sponsor/{sponsorId}")
    public void deleteSponsorPost(@PathVariable Long sponsorId) throws CustomException {
         postFacade.deleteSponsorPost(sponsorId);
    }


    /*@PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody Post post) throws PostException {
        postFacade.updatePost(id, post);
    }*/


    /* @GetMapping("/sponsor-user/{sponsorId}")
    public List<SponsorPostResponseDto> getAllSponsorPost(@PathVariable Long sponsorId){
        //search all post by Sponsor User
        return postFacade.getAllSponsorPost(sponsorId);
    }*/

}
