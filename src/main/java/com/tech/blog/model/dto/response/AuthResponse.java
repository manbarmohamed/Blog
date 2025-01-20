package com.tech.blog.model.dto.response;


import com.tech.blog.model.enums.Role;
import lombok.Data;

@Data
public class AuthResponse extends BaseResponse{

    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private Role role;
}
