package com.tech.blog.model.dto.response;

import com.tech.blog.model.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(name = "PostResponse", description = "Detailed post response")
public class PostResponse extends BaseResponse {
    @Schema(description = "Post ID", example = "1")
    private Long id;

    @Schema(description = "Post title", example = "Introduction to Spring Boot")
    private String title;

    @Schema(description = "Post content", example = "<p>Spring Boot makes it easy...</p>")
    private String content;

    @Schema(description = "Cover image URL", example = "https://example.com/image.jpg")
    private String coverImageUrl;

    @Schema(description = "Post status", example = "PUBLISHED")
    private PostStatus status;

    @Schema(description = "Number of views", example = "150")
    private int views;

    @Schema(description = "Author summary")
    private UserSummaryResponse author;

    @Schema(description = "Category details")
    private CategoryResponse category;

    @Schema(description = "List of tags")
    private List<TagResponse> tags;

    @Schema(description = "List of comments")
    private List<CommentResponse> comments;

    @Schema(description = "Number of comments", example = "10")
    private int commentsCount;

    @Schema(description = "Number of likes", example = "25")
    private int likesCount;
}