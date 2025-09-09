package com.notification.service.response;

public record DutyNotificationResponse(
        String firstName,
        String lastName,
        String userEmail,
        String duty
) {
}
