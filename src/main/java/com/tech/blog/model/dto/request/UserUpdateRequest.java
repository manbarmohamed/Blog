package com.tech.blog.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    @Email(message = "Email should be valid")
    private String email;
}