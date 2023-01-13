package com.example.trainogram.service.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
public class EmailController {

    private final EmailSenderImpl emailSender;

    public EmailController(EmailSenderImpl emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public void sendEmail(List<String> to, String subject, String text) throws MessagingException, IOException {
            emailSender.sendEmail(to,subject,text);
    }

}
