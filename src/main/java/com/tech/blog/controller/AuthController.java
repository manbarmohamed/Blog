package com.tech.blog.controller;

import com.tech.blog.model.dto.request.LoginRequest;
import com.tech.blog.model.dto.request.RegisterRequest;
import com.tech.blog.model.dto.response.AuthResponse;
import com.tech.blog.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest signupDto) {
        authService.registerUser(signupDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDto) {
        AuthResponse user = authService.LoginUser(loginDto);
        return ResponseEntity.ok(user);
    }

}