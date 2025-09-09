package com.duty.service.user;

public record UserResponse(
        Integer userId,
        String firstName,
        String lastName,
        String email
) {
}
