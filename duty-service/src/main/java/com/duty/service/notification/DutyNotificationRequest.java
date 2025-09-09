package com.duty.service.notification;

public record DutyNotificationRequest(
        String firstName,
        String lastName,
        String userEmail,
        String duty
) {
}
