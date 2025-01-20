package com.tech.blog.model.dto.response;

import com.tech.blog.model.enums.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostStatusResponse {
    private Long id;
    private PostStatus status;
    private LocalDateTime updatedAt;
    private String updatedBy;
}