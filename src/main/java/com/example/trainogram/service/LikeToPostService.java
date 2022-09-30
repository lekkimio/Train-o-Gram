package com.example.trainogram.service;

import com.example.trainogram.exception.CustomException;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeToPostService {
    List<Post> findAllLikedPostsByUser(User user);

    List<User> findAllLikedUsersToPost(Long postId);

    void addLikeToPost(Post post, User user) throws CustomException;

    void deleteLikeFromPost(Post post, User user);
}
