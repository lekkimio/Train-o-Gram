package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;



@Service
public class PictureService {


    private final String folder = "D:\\Games\\pictures\\";

    public String saveUserImage(MultipartFile file, Long userId) throws IOException {
        String userPath = folder + userId;
        return saveProcess(file, userPath);
    }

    public List<String> savePostImage(List<MultipartFile> file, Long userId, Long postId) throws IOException {
        String userPath = folder + userId + "\\post\\" + postId;
        List<String> pictures = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            pictures.add(saveProcess(multipartFile, userPath));
        }
        return pictures;
    }

    public String saveProcess(MultipartFile file, String userPath) throws IOException {
        byte[] bytes = file.getBytes();

        File file1 = new File(userPath);
        file1.mkdirs();
        String type = "."+ Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        UUID fileName = UUID.randomUUID();

        String finalFileName = fileName + type;

        Path path = Paths.get(userPath + File.separator + finalFileName);
        Files.write(path, bytes);

        return finalFileName;

//        return "file://"+path;
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


    public InputStreamResource getUserAvatar(User user) throws IOException {
        String path = user.getId()+File.separator+user.getAvatar();

        var imgFile = new ClassPathResource(path);
        boolean boo = imgFile.exists();

        if (!boo) {
            imgFile = new ClassPathResource(File.separator+"user_picture.jpg");
        }

        return new InputStreamResource(imgFile.getInputStream());
    }
}
