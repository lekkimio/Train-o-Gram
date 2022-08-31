package com.example.trainogram.service.impl;

import com.example.trainogram.model.Picture;
import com.example.trainogram.repository.PictureRepository;
import com.example.trainogram.service.PictureService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture addPicture(MultipartFile file) throws IOException {
        Picture picture = new Picture();
        picture.setContent(file.getBytes());
        picture.setCreated(LocalDateTime.now());



        return pictureRepository.save(picture);

    }

    @Override
    public Picture updatePicture(Picture picture, Long id) {
        Picture pictureToUpdate = pictureRepository.findById(id).orElse(null);
        pictureToUpdate.setContent(picture.getContent());
        return pictureRepository.save(pictureToUpdate);
    }

    @Override
    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

    @Override
    public byte[] getPicture(Long id) {
        return pictureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();
    }

    @Override
    public byte[] findPictureById(Long id) {
         return pictureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();
    }


    /*@Override
    public void addPostPicture(User user, Post post, Picture picture) {
    }

    @Override
    public void updatePostPicture(User user, Post post, Picture picture) {
    }

    @Override
    public void deletePostPicture(User user, Post post) {
    }

    @Override
    public void addUserAvatar(User user, Picture picture) {
    }

    @Override
    public void deleteUserAvatar(User user) {
        pictureRepository.delete(user.getAvatar())
    }

    @Override
    public void updateUserAvatar(User user, Picture picture) {

    }

    @Override
    public Picture getPostPicture(User user, Post post) {

    }

    @Override
    public Picture getUserAvatar(User user) {

    }*/
}
