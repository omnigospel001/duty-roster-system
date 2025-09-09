package com.user.service.service;

import com.user.service.entity.User;
import com.user.service.mapper.Mapper;
import com.user.service.repository.UserRepo;
import com.user.service.request.UserRequest;
import com.user.service.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final Mapper mapper;


    public User saveUser(UserRequest userRequest) {
        return userRepo.save(mapper.saveUser(userRequest));
    }

    public UserResponse findByUserId(Integer userId) {
        return userRepo.findByUserId(userId).map(mapper::findByUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not registered"));
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
