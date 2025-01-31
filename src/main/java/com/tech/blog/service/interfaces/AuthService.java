package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.request.LoginRequest;
import com.tech.blog.model.dto.request.RegisterRequest;
import com.tech.blog.model.dto.response.AuthResponse;

public interface AuthService {

    void registerUser(RegisterRequest registerRequest);

    AuthResponse loginUser(LoginRequest loginRequest);
}
