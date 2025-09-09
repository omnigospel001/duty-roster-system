package com.notification.service.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.subject}")
    private String subjectEmail;


    public void sendDutEmailNotification(String firstName,
                                         String lastName,
                                         String userEmail,
                                         String duty) {

            try {

                MimeMessage message = mailSender.createMimeMessage();
                var messageHelper = new MimeMessageHelper(message);

                messageHelper.setFrom(senderEmail, firstName + " " + lastName);
                messageHelper.setTo(userEmail);
                messageHelper.setSubject(subjectEmail);
                messageHelper.setText(duty, true);

                mailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
