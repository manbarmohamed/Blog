package com.tech.blog.model.dto.response;

import com.tech.blog.model.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.tech.blog.model.entity.Post}
 */
@Getter
@Setter
@AllArgsConstructor
public class PostResponse {
    Long id;
    String title;
    String content;
    String imageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    PostStatus status;
    int views;
    private UserSummaryResponse author;
    CategoryResponse category;
    private List<TagResponse> tags;
    private List<CommentResponse> comments;
    private int likesCount;
}