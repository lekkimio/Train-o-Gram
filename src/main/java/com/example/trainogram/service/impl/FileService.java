package com.example.trainogram.service.impl;

import com.example.trainogram.model.Post;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {


    private final String folder = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\";

    public String saveUserImage(MultipartFile file, Long userId) throws IOException {
        String userPath = folder + userId;
        return saveProcess(file, userPath);
    }

    public String savePostImage(MultipartFile file, Long userId, Long postId) throws IOException {
        String userPath = folder + userId + "\\post\\" + postId;
        return saveProcess(file, userPath);
    }

    public String saveProcess(MultipartFile file, String userPath) throws IOException {
        byte[] bytes = file.getBytes();

        File file1 = new File(userPath);
        file1.mkdirs();
        String type = "."+ Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        UUID fileName = UUID.randomUUID();

        String finalFileName = fileName + type;

        Path path = Paths.get(userPath + File.separator + finalFileName);
//        FileUtils.cleanDirectory(file1);
        Files.write(path, bytes);

        return finalFileName;
    }

    public boolean deleteUserFiles(Long id) throws IOException {
        File f = new File(folder + id);
        FileUtils.deleteDirectory(f);
        return !f.exists();
    }

    public boolean deletePostFiles(Post post) throws IOException {
            File f = new File(folder+post.getPostAuthor().getId()+"\\post\\"+post.getId());
            FileUtils.deleteDirectory(f);
            return !f.exists();
    }

}
