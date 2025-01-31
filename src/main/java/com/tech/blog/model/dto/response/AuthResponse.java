package com.tech.blog.model.dto.response;

import com.tech.blog.model.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse extends BaseResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private Long expiresIn;

    @Builder.Default
    private String status = "success";
}