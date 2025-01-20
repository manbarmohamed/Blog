package com.tech.blog.model.dto.response;

import lombok.Data;

@Data
public class UserSummaryResponse {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
}