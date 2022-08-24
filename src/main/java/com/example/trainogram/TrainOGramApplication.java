package com.example.trainogram;

import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainOGramApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainOGramApplication.class, args);
    }



}
