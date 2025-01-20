package com.tech.blog.model.dto.request;

import com.tech.blog.model.enums.PostStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostStatusRequest {
    @NotNull(message = "Post status is required")
    private PostStatus status;
}
