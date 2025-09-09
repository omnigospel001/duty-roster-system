package com.user.service.response;

public record UserResponse(
        Integer userId,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
