package com.tech.blog.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse extends BaseResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private int postsCount;
}