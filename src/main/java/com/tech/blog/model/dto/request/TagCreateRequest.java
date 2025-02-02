package com.tech.blog.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class TagCreateRequest {
    @NotBlank(message = "Tag name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9-\\s]{2,50}$", message = "Tag name must be 2-50 characters long and contain only letters, numbers, hyphens, and spaces")
    @Schema(description = "Tag name", example = "spring-boot")
    private String name;
}