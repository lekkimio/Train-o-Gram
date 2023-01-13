package com.example.trainogram.controller;


import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status439CommentNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.UserResponseDto;
import com.example.trainogram.service.impl.LikeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LikeController {

    private final ModelMapper modelMapper;
    private final LikeService likeService;



   /* @GetMapping("/{postId}")
    public List<Like> getAllLikedUsers(@PathVariable Long postId) {
        return likeFacade.findAllLikedUsers(postId);
    }*/

    @GetMapping("/post/like/{postId}")
    public List<UserResponseDto> getAllLikedUsersToPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                                        @PathVariable Long postId) {
        List<User> users = likeService.findAllLikedUsersToPost(postId);
        Type listType = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,listType);

    }

    @GetMapping("/post/like")
    public List<PostResponseDto> getAllLikedPostsByUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        List<Post> posts = likeService.findAllLikedPostsByUser(token);
        Type listType = new TypeToken<List<PostResponseDto>>(){}.getType();
        return modelMapper.map(posts,listType);
    }

    @GetMapping("/comment/like/{commentId}")
    public List<UserResponseDto> getAllLikedUsersToComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                                           @PathVariable Long commentId) {
        List<User> users = likeService.findAllLikedUsersToComment(token, commentId);
        Type listType = new TypeToken<List<UserResponseDto>>(){}.getType();
        return modelMapper.map(users,listType);

    }

    @PostMapping("/post/like/{postId}")
    public void addLikeToPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                              @PathVariable Long postId) throws Status437PostNotFound {
        likeService.addLikeToPost(token,postId);

    }

    @DeleteMapping("/post/like/{postId}")
    public void deleteLikeFromPost(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) throws Status437PostNotFound {
        likeService.deleteLikeFromPost(token, postId);
    }

    @PostMapping("/comment/like/{commentId}")
    public void addLikeToComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                 @PathVariable Long commentId) throws Status439CommentNotFound {
        likeService.addLikeToComment(token,commentId);
    }

    @DeleteMapping("/comment/like/{commentId}")
    public void deleteLikeFromComment(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                      @PathVariable Long commentId) throws Status435NoAuthorities, Status439CommentNotFound {
        likeService.deleteLikeFromComment(token, commentId);
    }
}
