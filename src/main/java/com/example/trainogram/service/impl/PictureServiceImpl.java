package com.example.trainogram.service.impl;

import com.example.trainogram.model.Picture;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.PictureRepository;
import com.example.trainogram.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void addPicture(User user, Post post, Picture picture) {

        pictureRepository.save(picture);

    }

    @Override
    public void deletePicture(User user, Post post, Picture picture) {
        pictureRepository.delete(picture);
    }
}
