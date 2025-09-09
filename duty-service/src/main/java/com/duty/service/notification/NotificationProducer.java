package com.duty.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, DutyNotificationRequest> kafkaTemplate;

    public void sendNotification(DutyNotificationRequest notificationRequest) {
        log.info("Sending notification with body = : {} :", notificationRequest);
        Message<DutyNotificationRequest> message = MessageBuilder
                .withPayload(notificationRequest)
                .setHeader(TOPIC, "schedule-topic")
                .build();

        kafkaTemplate.send(message);
    }

}
