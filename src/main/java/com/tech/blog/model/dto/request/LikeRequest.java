package com.tech.blog.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class LikeRequest {
    @NotNull(message = "User ID is required")
    private Long userId;
}