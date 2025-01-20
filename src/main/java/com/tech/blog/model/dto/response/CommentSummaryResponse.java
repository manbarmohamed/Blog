package com.tech.blog.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentSummaryResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private PostSummaryResponse post;
}