package com.tech.blog.service.impl;

import com.tech.blog.mapper.UserMapper;
import com.tech.blog.model.dto.request.LoginRequest;
import com.tech.blog.model.dto.request.RegisterRequest;
import com.tech.blog.model.dto.response.AuthResponse;
import com.tech.blog.model.entity.User;
import com.tech.blog.model.enums.Role;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.interfaces.AuthService;
import com.tech.blog.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Override
    public void registerUser(RegisterRequest registerRequest) {
        User user = userMapper.toEntity(registerRequest);
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthResponse LoginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getUsername());
            String token = jwtUtils.generateToken(user, user.getRole());

            return AuthResponse.builder()
                    .accessToken(token)
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request.");
        }
    }
}
