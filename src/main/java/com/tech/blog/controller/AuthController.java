package com.tech.blog.controller;

import com.tech.blog.model.dto.request.LoginRequest;
import com.tech.blog.model.dto.request.RegisterRequest;
import com.tech.blog.model.dto.response.AuthResponse;
import com.tech.blog.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController handles authentication operations including user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;


    @Operation(summary = "User Registration", description = "Registers a new user and returns a success message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest signupDto) {
        authService.registerUser(signupDto);
        return ResponseEntity.ok("User registered successfully");
    }


    @Operation(summary = "User Login", description = "Logs in an existing user and returns an authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDto) {
        AuthResponse user = authService.LoginUser(loginDto);
        return ResponseEntity.ok(user);
    }
}