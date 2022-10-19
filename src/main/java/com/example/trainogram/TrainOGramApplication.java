package com.example.trainogram;


import com.example.trainogram.service.impl.FileService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

@SpringBootApplication
public class TrainOGramApplication implements CommandLineRunner {


    private final FileService fileService;

    public TrainOGramApplication(FileService fileService) {
        this.fileService = fileService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TrainOGramApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        File file = new File("D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\1\\2.jpg");
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            // Or faster..
            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
        } catch (IOException ex) {
            // do something.
        }

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println(fileService.saveProcess(multipartFile, "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\100"));;
    }

}
