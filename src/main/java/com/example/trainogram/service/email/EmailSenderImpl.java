package com.example.trainogram.service.email;

import com.example.trainogram.model.User;
import com.example.trainogram.service.UserService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Service
@EnableScheduling
public class EmailSenderImpl implements EmailSender {


    private final JavaMailSenderService mailSender;

    public EmailSenderImpl(JavaMailSenderService mailSender) {
        this.mailSender = mailSender;}

    private final String path = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\templates\\";

    /*@Scheduled(fixedDelay = *//*1000 * 60 * 60 * 24 * 7*//* (1000*60*60)/10)
    public void sendWeeklyReport() throws MessagingException, javax.mail.MessagingException, IOException {
        List<User> users = userService.findAllUsers();

        sendEmail(new String[]{"sidqk2002@gmail.com"}, "Congrats", "temp");
//        users.forEach(user -> sendEmail(user.getEmail()));
    }*/

    @Override
    public void sendEmail(String toEmail) throws MessagingException, javax.mail.MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(toEmail);
        mimeMessage.setFrom("lekkimio15@gmail.com");
        mimeMessage.setSubject("Weekly Report");


        File file = new File(path + "test.html");
        String text = Files.readString(file.toPath());
        mimeMessage.setContent(text, "text/html");

//        String htmlMsg = ;
//        mimeMessage.setContent(htmlMsg, "text/html");
//        helper.set
//        mimeMessage.setFileName("message");
//        helper.setText(htmlMsg, true); // Use this or above line.
        mailSender.getJavaMailSender().send(mimeMessage);

//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("lekkimio15@gmail.com");
//        message.setSubject("Weekly Report");
//        message.set;
//
//
//        for (String user : to) {
//            message.setTo(user);
//            mailSender.getJavaMailSender().send(message);
//            System.out.println("Mail sent....");
//        }


    }


    @Override
    public void sendEmail(List<String> to, String subject, String template) throws javax.mail.MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        mimeMessage.setFrom("lekkimio15@gmail.com");
        mimeMessage.setSubject(subject);

        File file = new File(path + template);

        String text = Files.readString(file.toPath());
        mimeMessage.setContent(text, "text/html");

        for (String user : to) {
            helper.setTo(user);
            mailSender.getJavaMailSender().send(mimeMessage);
            System.out.println("Mail sent....");
        }


    }

    @Override
    public void sendEmail(String to, String subject, String template) throws javax.mail.MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        mimeMessage.setFrom("lekkimio15@gmail.com");
        mimeMessage.setSubject(subject);
        File file = new File(path + template);
        String text = Files.readString(file.toPath());
        mimeMessage.setContent(text, "text/html");

            helper.setTo(to);
            mailSender.getJavaMailSender().send(mimeMessage);
            System.out.println("Mail sent....");


    }



    // TODO: 16.12.2022  Create service to send email to user; subscription system


}

