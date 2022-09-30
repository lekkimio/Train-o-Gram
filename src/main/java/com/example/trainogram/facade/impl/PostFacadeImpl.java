package com.example.trainogram.facade.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.facade.PostFacade;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.response.PostResponseDto;
import com.example.trainogram.model.dto.response.SponsorPostResponseDto;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class PostFacadeImpl implements PostFacade {

    private String folder = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\";

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
    public PostResponseDto createPost(String postText, MultipartFile file) throws IOException {
        /*User user = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostAuthor(user);
        post.setPostText(postText);

        saveImage(file, user.getId());
        post.setPostPicture(file.getOriginalFilename());

        Post savedPost = postService.addPost(post);

        return mapToDto.map(savedPost, PostDto.class);*/

        User user = userService.findAuthenticatedUser();
        /*Post post = new Post();
        post.setPostAuthor(user);
        post.setPostText(postText);*/
        Post savedPost = postService.addPost(Post.builder()
                .postAuthor(user)
                .postText(postText)
                .likes(0)
                .build()
        );

        saveImage(file, user.getId(), savedPost.getId());
        savedPost.setPostPicture(file.getOriginalFilename());
        postService.updatePost(savedPost);

        return mapToDto.map(savedPost, PostResponseDto.class);
    }

    @Override
    public void deletePost(Long id) throws CustomException {
        User user = userService.findAuthenticatedUser();
        Post post = postService.findByPostId(id);
            if (Objects.equals(post.getPostAuthor().getId(), user.getId())) {
                postService.deletePost(id);
            } else {
                throw new CustomException("You can't delete this post", HttpStatus.UNAUTHORIZED);
            }
    }

    @Override
    public List<PostResponseDto> getAllUserPosts() {

        User user = userService.findAuthenticatedUser();
        List<Post> posts = postService.findAllPosts(user.getId());
        Type listType = new TypeToken<List<PostResponseDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }

    @Override
    public PostResponseDto getUserPostById(Long id) {

        return mapToDto.map(postService.findByPostId(id),PostResponseDto.class);
    }

    /*@Override
    public PostResponseDto updatePost(Long id, Post post) throws PostException {
        Post postToUpdate = postService.findByPostId(id);
        User author = postToUpdate.getPostAuthor();
        User editor = userService.findAuthenticatedUser();

        if (Objects.equals(author.getId(), editor.getId())) {
            return mapToDto.map(postService.updatePost(id, post), PostResponseDto.class);
        } else {
            throw new PostException("You are not allowed to edit this post");
        }
    }*/


    @Override
    public SponsorPostResponseDto createSponsorPost(String postText, MultipartFile file, Long sponsorId) throws IOException, CustomException {
        /*User author = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostText(postText);
        post.setPostAuthor(author);
        try {
            post.setPostPicture(saveImage(file, author.getId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SponsorPost sponsorPost;
        try {
            sponsorPost = sponsorPostService.addSponsorPost(postService.addPost(post), userService.findUserById(sponsorId));
        } catch (UserException e) {
            throw new RuntimeException(e);
        }

        return mapToDto.map(sponsorPost, SponsorPostDto.class);*/

        User author = userService.findAuthenticatedUser();
        Post post = new Post();
        post.setPostText(postText);
        post.setPostAuthor(author);
        Post savedPost = postService.addPost(post);
        saveImage(file, author.getId(), savedPost.getId());
            savedPost.setPostPicture(file.getOriginalFilename());
            postService.updatePost(savedPost);

        SponsorPost sponsorPost;
        try {
            sponsorPost = sponsorPostService.addSponsorPost(savedPost, userService.findUserById(sponsorId));
        } catch (CustomException e) {
            throw new CustomException("Post not found", HttpStatus.NOT_FOUND);
        }

        return mapToDto.map(sponsorPost, SponsorPostResponseDto.class);
    }

    @Override
    public SponsorPostResponseDto getSponsorPost(Long sponsorPostId) {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(sponsorPostId);
        return mapToDto.map(sponsorPost,SponsorPostResponseDto.class);
    }

    @Override
    public List<SponsorPostResponseDto> getAllSponsorPost(Long sponsorId) {

        List<SponsorPost> posts = sponsorPostService.findAllSponsorPostsByUser(sponsorId);
        Type listType = new TypeToken<List<SponsorPostResponseDto>>(){}.getType();
        return mapToDto.map(posts,listType);
    }

    /*@Override
    public void updateSponsorPost(Long id, Post post) {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(id);
        if (post.getPostText() != null) {
            sponsorPost.getPost().setPostText(post.getPostText());
        }

       sponsorPostService.updateSponsorPost(sponsorPost);
    }*/

    @Override
    public void deleteSponsorPost(Long sponsorId) throws CustomException {
        Post post = sponsorPostService.findBySponsorPostId(sponsorId).getPost();

        User user = userService.findAuthenticatedUser();
        if (user.equals(post.getPostAuthor())){


        sponsorPostService.deleteSponsorPost(sponsorId);
        postService.deletePost(post.getId());

        }else {
            throw new CustomException("You can't delete this", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public byte[] getPostPicture(Long id) {
        Post post = postService.findByPostId(id);

        String path = "/static/"+post.getPostAuthor().getId()+"/post/"+id+"/"+post.getPostPicture();
        System.out.println(path);
        InputStream in = getClass().getResourceAsStream(path);
        assert in != null;
        byte[] result = null;
        try {
            result = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updatePost(Long id, String postText, MultipartFile newFile) throws IOException, CustomException {
       Post post = postService.findByPostId(id);
       User user = userService.findAuthenticatedUser();
        if (user.equals(post.getPostAuthor())) {
            if (newFile != null){
                post.setPostPicture(saveImage(newFile, user.getId(), post.getId()));
            }
            if (!postText.isEmpty()) {
                post.setPostText(postText);
            }
            postService.updatePost(post);
        } else {
            throw new CustomException("You cant edit this post", HttpStatus.UNAUTHORIZED);
        }
    }

    protected String saveImage(MultipartFile file, Long userId, Long postId) throws IOException {
        byte[] bytes = file.getBytes();
        String userPath = folder+userId.toString()+"\\post\\"+postId;
        new File(userPath).mkdirs();
        Path path = Paths.get(userPath+File.separator+file.getOriginalFilename());
        Files.write(path,bytes);
        return file.getOriginalFilename();
    }

    @Override
    public void updateSponsorPost(Long sponsorPostId, String postText, MultipartFile file) throws IOException {
        SponsorPost sponsorPost = sponsorPostService.findBySponsorPostId(sponsorPostId);
        User user = userService.findAuthenticatedUser();
        if (user.equals(sponsorPost.getPost().getPostAuthor())){
            if (postText != null) {
                sponsorPost.getPost().setPostText(postText);
            }
            if (!file.isEmpty()) {
                sponsorPost.getPost().setPostPicture(saveImage(file, user.getId(), sponsorPost.getPost().getId()));
            }
            sponsorPostService.updateSponsorPost(sponsorPost);
        }
    }
}

