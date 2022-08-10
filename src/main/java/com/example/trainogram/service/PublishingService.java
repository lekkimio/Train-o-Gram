package com.example.trainogram.service;

import com.example.trainogram.model.Picture;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

public interface PublishingService {

    Post publishPost(User author, Picture picture, String text);
}
