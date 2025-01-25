package com.tech.blog.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse", description = "Standard error response format")
public class ErrorResponse {

    @Schema(description = "Timestamp of error", example = "2024-02-20T14:30:45.123Z")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error type", example = "Not Found")
    private String error;

    @Schema(description = "Detailed error message", example = "Post with id 123 not found")
    private String message;
}