package com.tech.blog.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse extends BaseResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserSummaryResponse author;
    private PostSummaryResponse post;
}