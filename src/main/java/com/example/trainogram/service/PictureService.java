package com.example.trainogram.service;

import com.example.trainogram.model.Picture;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;

public interface PictureService {

    void addPicture(User user, Post post, Picture picture);

    void deletePicture(User user, Post post, Picture picture);

}
