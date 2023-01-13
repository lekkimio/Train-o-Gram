package com.example.trainogram.controller;

import com.example.trainogram.service.UserService;
import com.example.trainogram.service.email.EmailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@EnableScheduling
public class EmailSendingController {

    private final EmailSender emailService;
    private final UserService userService;


    public EmailSendingController(EmailSender emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    // TODO: 26.12.2022 remove comment
//    @Scheduled(fixedDelay = 60000 * 60 * 24)
    private void sendDailyQuotes() throws MessagingException, IOException {
        emailService.sendEmail(userService.getAllUserEmails(),"Update for you!" ,"daily_quotes.html");
    }

}
