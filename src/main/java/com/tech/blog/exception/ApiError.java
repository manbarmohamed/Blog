package com.tech.blog.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Standard error response")
public class ApiError {
    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Detailed error description")
    private String details;
}