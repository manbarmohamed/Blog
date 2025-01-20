package com.tech.blog.model.dto.response;


import com.tech.blog.model.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse extends BaseResponse{

    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
    private Boolean isActive;

}
