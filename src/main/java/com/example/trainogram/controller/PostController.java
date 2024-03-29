package com.example.trainogram.controller;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status438SponsorPostNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.dto.SponsorReport;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.SponsorPostResponseDto;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final SponsorPostService sponsorPostService;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<PostResponseDto> getAllUserPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        List<Post> posts = postService.findAllPostsByUser(token);

        Type listType = new TypeToken<List<PostResponseDto>>() {}.getType();
        return modelMapper.map(posts, listType);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PostResponseDto getUserPost(@PathVariable Long id) throws Status437PostNotFound {
        return modelMapper.map(postService.findPostById(id), PostResponseDto.class);
    }


    @PostMapping()
    public void createPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                           @RequestParam(required = false) String postText,
                           @RequestParam List<MultipartFile> postPicture) throws IOException {
        postService.createPost(token, postText, postPicture);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) throws Status435NoAuthorities, Status437PostNotFound, IOException {
        postService.deletePost(token, id);
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                           @RequestParam(required = false) String postText,
                           @RequestParam(required = false) List<MultipartFile> file,
                           @PathVariable Long id) throws Status437PostNotFound, IOException, Status435NoAuthorities {
        postService.updatePost(token, id, postText, file);
    }

    /**
    Sponsor posts
    **/

    @GetMapping("/sponsor/{sponsorPostId}")
    public SponsorPostResponseDto getSponsorPost(@PathVariable Long sponsorPostId) throws Status438SponsorPostNotFound {
        //find Post by SponsorPostId
        return modelMapper.map(sponsorPostService.findSponsorPost(sponsorPostId), SponsorPostResponseDto.class);
    }



    @PostMapping("/sponsor")
    public void createSponsorPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                  @RequestParam String postText,
                                  @RequestParam Long sponsorId,
                                  @RequestParam List<MultipartFile> file) throws Status434UserNotFound, IOException {
        sponsorPostService.createSponsorPost(token, postText,file, sponsorId);
    }

    @PutMapping("/sponsor/{sponsorPostId}")
    public void updateSponsorPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                  @PathVariable Long sponsorPostId,
                                  @RequestParam String postText,
                                  @RequestParam List<MultipartFile> file) throws IOException, Status438SponsorPostNotFound, Status435NoAuthorities, Status437PostNotFound {
        sponsorPostService.updateSponsorPost(token, sponsorPostId, postText, file);
    }

    @DeleteMapping("/sponsor/{sponsorPostId}")
    public void deleteSponsorPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                  @PathVariable Long sponsorPostId) throws Status435NoAuthorities, Status438SponsorPostNotFound, Status437PostNotFound, IOException {
        sponsorPostService.deleteSponsorPost(token, sponsorPostId);
    }


//    @PutMapping("/{id}")
//    public void updateSponsorPost(@PathVariable Long id, String postText, MultipartFile file) throws CustomException, IOException {
//        sponsorPostService.updateSponsorPost(id, postText, file);
//    }


    @GetMapping("/sponsor-user/{sponsorId}")
    public List<SponsorPostResponseDto> getAllSponsorPost(@PathVariable Long sponsorId) throws Status434UserNotFound {
        //search all post by Sponsor User
         List<SponsorPost> sponsorPosts = sponsorPostService.findAllSponsorPost(sponsorId);
         Type listType = new TypeToken<List<SponsorPostResponseDto>>() {}.getType();
        return modelMapper.map(sponsorPosts, listType);
    }

    @GetMapping("/sponsor")
    public List<SponsorPostResponseDto> getAllSponsorPosts(){
        //search all post by Sponsor User
        List<SponsorPost> sponsorPosts = sponsorPostService.findAllSponsorPosts();
        Type listType = new TypeToken<List<SponsorPostResponseDto>>() {}.getType();
        return modelMapper.map(sponsorPosts, listType);
    }


    @GetMapping("/sponsor/report")
    public List<SponsorReport> getReport(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws Status434UserNotFound {
        return sponsorPostService.getReport(token);
    }

}
