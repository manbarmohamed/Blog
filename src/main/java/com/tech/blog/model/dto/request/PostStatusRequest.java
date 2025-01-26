package com.tech.blog.model.dto.request;

import com.tech.blog.model.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(name = "PostStatusRequest", description = "Request to update post status")
public class PostStatusRequest {

    @Schema(description = "New post status",
            allowableValues = {"DRAFT", "PUBLISHED", "ARCHIVED"},
            example = "PUBLISHED",
            requiredMode = REQUIRED)
    @NotNull
    private PostStatus status;
}