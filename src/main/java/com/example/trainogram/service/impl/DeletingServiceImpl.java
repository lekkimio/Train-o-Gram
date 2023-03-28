package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.service.DeletingService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DeletingServiceImpl implements DeletingService {
    private final PostService postService;

    public DeletingServiceImpl(PostService postService) {
        this.postService = postService;
    }


    @Override
    public void deleteUser(User deletedUser) throws Status434UserNotFound {

        deletedUser.setUsername("DELETED");
        deletedUser.setPassword("deleted");
        deletedUser.setEmail("DELETED");
        deletedUser.setAvatar("DELETED");


    }
}
