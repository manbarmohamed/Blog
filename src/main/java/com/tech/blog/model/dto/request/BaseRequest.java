package com.tech.blog.model.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequest {
    @NotNull 
    private LocalDateTime timestamp = LocalDateTime.now();
}
