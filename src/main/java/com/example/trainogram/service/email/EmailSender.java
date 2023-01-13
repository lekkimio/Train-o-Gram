package com.example.trainogram.service.email;

import com.example.trainogram.exception.Status434UserNotFound;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.util.List;

public interface EmailSender {
    void sendEmail(String toEmail) throws MessagingException, Status434UserNotFound, javax.mail.MessagingException, IOException;

    void sendEmail(List<String> to, String subject, String template) throws javax.mail.MessagingException, IOException;

    void sendEmail(String to, String subject, String template) throws javax.mail.MessagingException, IOException;
}
