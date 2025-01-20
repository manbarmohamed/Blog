package com.tech.blog.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CommentCreateRequest extends BaseRequest {
    @NotBlank(message = "Content is required")
    @Size(min = 1, max = 1000, message = "Comment must be between 1 and 1000 characters")
    private String content;

    @NotNull(message = "Post ID is required")
    private Long postId;
}