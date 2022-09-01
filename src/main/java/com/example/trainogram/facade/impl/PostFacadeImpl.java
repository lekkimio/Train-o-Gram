package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.exception.UserException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class PostFacadeImpl implements PostFacade {

    private String folder = "E:\\Projects\\Java Learn\\Train-o-Gram\\src\\main\\resources\\static\\";

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final SponsorPostService sponsorPostService;
    private final ModelMapper mapToDto;

    public PostFacadeImpl(@Qualifier("postServiceImpl") PostService postService, UserService userService, CommentService commentService, SponsorPostService sponsorPostService, ModelMapper mapToDto) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.sponsorPostService = sponsorPostService;
        this.mapToDto = mapToDto;
    }

    @Override
    public PostDto addPost(String postText, MultipartFile file) throws IOException {
        User user = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostAuthor(user);
        post.setPostText(postText);
        Post savedPost = postService.addPost(post);

        saveImage(file, user.getId(), savedPost.getId());
        savedPost.setPostPicture(file.getOriginalFilename());
        postService.updatePost(savedPost.getId(), savedPost);

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
    public SponsorPostDto addSponsorPost(String postText, MultipartFile file, Long sponsorId) {
        User author = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostText(postText);
        post.setPostAuthor(author);
        Post savedPost = postService.addPost(post);
        try {
            savedPost.setPostPicture(saveImage(file, author.getId(), post.getId()));
            postService.updatePost(savedPost.getId(), savedPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SponsorPost sponsorPost;
        try {
            sponsorPost = sponsorPostService.addSponsorPost(savedPost, userService.findUserById(sponsorId));
        } catch (UserException e) {
            throw new RuntimeException(e);
        }

        return mapToDto.map(sponsorPost, SponsorPostDto.class);
    }

    @Override
    public SponsorPostDto getSponsorPost(Long sponsorPostId) {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(sponsorPostId);
        return mapToDto.map(sponsorPost,SponsorPostDto.class);
    }

    @Override
    public List<SponsorPostDto> getAllSponsorPost(Long sponsorId) {

        List<SponsorPost> posts = sponsorPostService.findAllSponsorPostsByUser(sponsorId);
        Type listType = new TypeToken<List<SponsorPostDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }

    @Override
    public SponsorPostDto updateSponsorPost(Long id, Post post) {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(id);
        Post newPost = sponsorPostService.findBySponsorPostId(id).getPost();
        newPost.setPostText(post.getPostText());
        sponsorPost.setPost(newPost);

        return mapToDto.map(sponsorPostService.updateSponsorPost(sponsorPost), SponsorPostDto.class);
    }

    @Override
    public void deleteSponsorPost(Long sponsorId) throws UserException {
        Post post = sponsorPostService.findBySponsorPostId(sponsorId).getPost();

        User user = userService.findAuthenticatedUser();
        if (user.equals(post.getPostAuthor())){


        sponsorPostService.deleteSponsorPost(sponsorId);
        postService.deletePost(post.getId());

        }else {
            throw new UserException("You can't delete this");
        }
    }

    protected String saveImage(MultipartFile file, Long userId, Long postId) throws IOException {
        byte[] bytes = file.getBytes();
        String userPath = folder+userId.toString()+"\\post\\"+postId;
        File dir = new File(userPath);

        boolean isDirectoryCreated = dir.mkdir();

        if(isDirectoryCreated)
            System.out.println("Directory created successfully");
        else
            System.out.println("Directory was not created successfully");

        Path path = Paths.get(userPath + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
        return file.getOriginalFilename();
    }


}

