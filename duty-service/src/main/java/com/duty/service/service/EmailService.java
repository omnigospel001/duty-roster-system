package com.duty.service.service;

import com.duty.service.notification.DutyNotificationRequest;
import com.duty.service.notification.NotificationProducer;
import com.duty.service.request.DutyRequest;
import com.duty.service.request.DutyRequestForMultiJobs;
import com.duty.service.user.UserClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.module.FindException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserClient userClient;

    private final NotificationProducer notificationProducer;

    //
    public void sendDutyReminderEmail(DutyRequest dutyRequest) throws UnsupportedEncodingException, MessagingException, MessagingException {

        var user = userClient.findByUserId(dutyRequest.getUserId())
                .orElseThrow(() -> new FindException("No User with this Id available"));

        this.notificationProducer.sendNotification(
               new DutyNotificationRequest(
                       user.firstName(),
                       user.lastName(),
                       user.email(),
                       dutyRequest.getDuty()
                )
        );

    }


    //
    public Runnable sendDutyReminderEmailForMultipleJobs(DutyRequestForMultiJobs requestForMultiJobs) throws UnsupportedEncodingException, MessagingException, MessagingException {

        return () -> {
            try {
                var user = userClient.findByUserId(requestForMultiJobs.getUserId())
                        .orElseThrow(() -> new FindException("No User with this Id available"));

                this.notificationProducer.sendNotification(
                        new DutyNotificationRequest(
                                user.firstName(),
                                user.lastName(),
                                user.email(),
                                requestForMultiJobs.getDuty()
                        )
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

    }

}


//--------------------------1-----------------------------------------
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom(senderEmail, user.firstName() +" "+ user.lastName());
//        messageHelper.setTo(user.email());
//        messageHelper.setSubject(subjectEmail);
//        messageHelper.setText(dutyRequest.getDuty(), true);
//
//        mailSender.send(message);

//------------------------2---------------------------
//                MimeMessage message = mailSender.createMimeMessage();
//                var messageHelper = new MimeMessageHelper(message);
//
//                messageHelper.setFrom(senderEmail, user.firstName() + " " + user.lastName());
//                messageHelper.setTo(user.email());
//                messageHelper.setSubject(subjectEmail);
//                messageHelper.setText(requestForMultiJobs.getDuty(), true);
//
//                mailSender.send(message);