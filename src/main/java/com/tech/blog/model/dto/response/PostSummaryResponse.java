package com.tech.blog.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSummaryResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private int views;
    private int commentsCount;
    private int likesCount;
}