package com.tech.blog.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Like status and count response")
public class LikeResponse {
    @Schema(description = "Total number of likes for the post")
    private long likeCount;

    @Schema(description = "Whether the current user has liked the post")
    private boolean hasUserLiked;

    @Schema(description = "Message describing the action taken")
    private String message;
}