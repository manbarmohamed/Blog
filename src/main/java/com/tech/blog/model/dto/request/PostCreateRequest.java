package com.tech.blog.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(name = "PostCreateRequest", description = "Request body for creating new post")
public class PostCreateRequest {
    @Schema(description = "Post title", example = "Introduction to Spring Boot", requiredMode = REQUIRED)
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @Schema(description = "Post content in HTML format", example = "<p>Spring Boot makes it easy...</p>", requiredMode = REQUIRED)
    @NotBlank
    private String content;

    @Schema(description = "Category ID", example = "1", requiredMode = REQUIRED)
    @NotNull
    private Long categoryId;

    @Schema(description = "List of tag IDs", example = "[1, 2, 3]")
    private List<Long> tagIds;
}