package com.example.trainogram.service.impl;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.LikeToPost;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.LikeToPostRepository;
import com.example.trainogram.service.LikeToPostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeToPostServiceImpl implements LikeToPostService {

    private final LikeToPostRepository likeToPostRepository;

    public LikeToPostServiceImpl(LikeToPostRepository likeToPostRepository) {
        this.likeToPostRepository = likeToPostRepository;
    }

    @Override
    public List<Post> findAllLikedPostsByUser(User user) {
        return likeToPostRepository.findAllPostsByUser(user);
    }

    @Override
    public List<User> findAllLikedUsersToPost(Long postId) {
        return likeToPostRepository.findAllUsersToPost(postId);
    }

    @Override
    public void addLikeToPost(Post post, User user) throws CustomException {
        if (likeToPostRepository.findLikeToPostByPostAndUser(post, user) == null) {
            likeToPostRepository.save(LikeToPost.builder()
                    .post(post)
                    .user(user)
                    .build());
        }
        else {
            throw new CustomException("You have already liked this post", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteLikeFromPost(Post post, User user) {
        LikeToPost likeToPost = likeToPostRepository.findLikeToPostByPostAndUser(post,user);
        if (likeToPost!=null)
        likeToPostRepository.deleteById(likeToPost.getId());
    }
}
