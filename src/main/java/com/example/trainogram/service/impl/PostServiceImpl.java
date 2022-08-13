package com.example.trainogram.service.impl;

import com.example.trainogram.model.Comment;
import com.example.trainogram.model.Post;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.service.CommentService;
import com.example.trainogram.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;

    public PostServiceImpl(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
    }

    @Override
    public Post addPost(Post post) {
        post.setPubDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findAllPosts(Long id) {
        return postRepository.findAllByUserId(id);
    }

    @Override
    public Post findByPostId(Long id) {
        return postRepository.findByPostId(id);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post postToUpdate = postRepository.findByPostId(id);
        if (!post.getPostText().isEmpty() || post.getPostPicture() != null) {
            postToUpdate.setPostText(post.getPostText());
            postToUpdate.setPostPicture(post.getPostPicture());
            postToUpdate.setLikes(post.getLikes());
        }

        return postRepository.save(postToUpdate);
    }

    @Override
    public void addComment(Long postId, Comment comment) {
        Post post = postRepository.findByPostId(postId);
        post.getComments().add(comment);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(Long post, Long comment) {
        Post postToUpdate = postRepository.findByPostId(post);

        Comment commentToDelete = commentService.findCommentById(comment);

        postToUpdate.getComments().remove(commentToDelete);
        postRepository.save(postToUpdate);
    }
}
