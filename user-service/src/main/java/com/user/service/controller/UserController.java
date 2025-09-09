package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.request.UserRequest;
import com.user.service.response.UserResponse;
import com.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok().body(userService.saveUser(userRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(userService.findByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

}
