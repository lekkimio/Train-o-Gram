package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.PostDto;
import com.example.trainogram.model.dto.SponsorPostDto;
import com.example.trainogram.service.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class PostFacadeImpl implements PostFacade {


    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final PictureService pictureService;
    private final SponsorPostService sponsorPostService;
    private final ModelMapper mapToDto;

    public PostFacadeImpl(@Qualifier("postServiceImpl") PostService postService, UserService userService, CommentService commentService, PictureService pictureService, SponsorPostService sponsorPostService, ModelMapper mapToDto) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.pictureService = pictureService;
        this.sponsorPostService = sponsorPostService;
        this.mapToDto = mapToDto;
    }

    @Override
    public PostDto addPost(String postText, MultipartFile file) throws IOException {
        User user = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostAuthor(user);
        post.setPostText(postText);
        post.setPostPicture(pictureService.addPicture(file));

        Post savedPost = postService.addPost(post);


        return mapToDto.map(savedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long id) throws PostException {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(id);
            if (Objects.equals(post.getPostAuthor().getId(), user.getId())) {
                postService.deletePost(id);
            } else {
                throw new PostException("You can't delete this post");
            }
    }

    @Override
    public List<PostDto> findAllPosts() {


        User user = userService.findAuthenticatedUser();

        List<Post> posts = postService.findAllPosts(user.getId());
        Type listType = new TypeToken<List<PostDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }

    @Override
    public PostDto findByPostId(Long id) {

        return mapToDto.map(postService.findByPostId(id),PostDto.class);
    }

    @Override
    public PostDto updatePost(Long id, Post post) throws PostException {
            Post postToUpdate = postService.findByPostId(id);
            User author = postToUpdate.getPostAuthor();
            User editor = userService.findAuthenticatedUser();

        if (Objects.equals(author.getId(), editor.getId())) {
                return mapToDto.map(postService.updatePost(id, post), PostDto.class);
        } else {
            throw new PostException("You are not allowed to edit this post");
        }
    }

    @Override
    public byte[] findPostPicture(Long id) {
        Post post = postService.findByPostId(id);
        return pictureService.findPictureById(post.getPostPicture().getId());
    }


    @Override
    public SponsorPostDto addSponsorPost(String postText, MultipartFile file, Long sponsorId) {
        Post post = new Post();
        post.setPostText(postText);
        try {
            post.setPostPicture(pictureService.addPicture(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SponsorPost sponsorPost = sponsorPostService.addSponsorPost(postService.addPost(post), sponsorId);

        return mapToDto.map(sponsorPost, SponsorPostDto.class);
    }

    @Override
    public SponsorPostDto getSponsorPost(Long sponsorPostId) {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(sponsorPostId);
        return mapToDto.map(sponsorPost,SponsorPostDto.class);
    }

    @Override
    public List<SponsorPostDto> getAllSponsorPost(Long sponsorId) {
        return null;
    }

    @Override
    public SponsorPostDto updateSponsorPost(Long sponsorId) {
        return null;
    }

    @Override
    public SponsorPostDto deleteSponsorPost(Long sponsorId) {
        return null;
    }

}

