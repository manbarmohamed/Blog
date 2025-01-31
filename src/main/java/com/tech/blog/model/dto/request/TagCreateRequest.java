package com.tech.blog.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class TagCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 30, message = "Tag name must be between 2 and 30 characters")
    private String name;
}