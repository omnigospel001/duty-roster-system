package com.notification.service.consumer;

import com.notification.service.response.DutyNotificationResponse;
import com.notification.service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "schedule-topic")
    public void consumeDutyEmailNotification(DutyNotificationResponse dutyNotificationResponse) {
        log.info(format("Consuming the message from schedule-topic Topic:: %s", dutyNotificationResponse));

        emailService.sendDutEmailNotification(
                dutyNotificationResponse.firstName(),
                dutyNotificationResponse.lastName(),
                dutyNotificationResponse.userEmail(),
                dutyNotificationResponse.duty()
        );
    }

}
