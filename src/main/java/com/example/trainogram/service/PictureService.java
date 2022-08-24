package com.example.trainogram.service;

import com.example.trainogram.model.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    /*void addPostPicture(User user, Post post, Picture picture);

    void updatePostPicture(User user, Post post, Picture picture);

    void deletePostPicture(User user, Post post);

    void addUserAvatar(User user, Picture picture);

    void deleteUserAvatar(User user);

    void updateUserAvatar(User user, Picture picture);

    Picture getPostPicture(User user, Post post);

    Picture getUserAvatar(User user);*/

    Picture addPicture(MultipartFile file) throws IOException;

    Picture updatePicture(Picture picture, Long id);

    void deletePicture(Long id);

    byte[] getPicture(Long id);

    byte[] findPictureById(Long id);
}
