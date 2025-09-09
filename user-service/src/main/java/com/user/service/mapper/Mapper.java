package com.user.service.mapper;

import com.user.service.entity.User;
import com.user.service.request.UserRequest;

import com.user.service.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder encoder;

    public User saveUser(UserRequest userRequest) {

        if (userRequest == null) {
            //Alt: Throw an exception here
            return null;
        }

        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .createdDate(LocalDateTime.now())
                .build();
    }

    public UserResponse findByUserId(User user) {

        if (user == null) {
            //Alt: Throw an exception here
            return null;
        }

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
