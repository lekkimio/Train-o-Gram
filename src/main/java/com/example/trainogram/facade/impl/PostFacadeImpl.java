package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.PostException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.PictureService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class PostFacadeImpl implements PostFacade {


    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final PictureService pictureService;

    public PostFacadeImpl(PostService postService, UserService userService, CommentService commentService, PictureService pictureService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.pictureService = pictureService;
    }

    @Override
    public Post addPost(String postText, MultipartFile file) throws IOException {
        User user = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostAuthor(user);
        post.setPostText(postText);
        post.setPostPicture(pictureService.addPicture(file));


        return postService.addPost(post);
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
    public List<Post> findAllPosts() {
        User user = userService.findAuthenticatedUser();

        return postService.findAllPosts(user.getId());
    }

    @Override
    public Post findByPostId(Long id) {
        return postService.findByPostId(id);
    }

    @Override
    public Post updatePost(Long id, Post post) throws PostException {
            Post postToUpdate = postService.findByPostId(id);
            User author = postToUpdate.getPostAuthor();
            User editor = userService.findAuthenticatedUser();

        if (Objects.equals(author.getId(), editor.getId())) {
                postService.updatePost(id, post);
        } else {
            throw new PostException("You are not allowed to edit this post");
        }

        return postToUpdate;
    }

    @Override
    public byte[] findPostPicture(Long id) {
        Post post = postService.findByPostId(id);
        return pictureService.findPictureById(post.getPostPicture().getId());
    }

}

