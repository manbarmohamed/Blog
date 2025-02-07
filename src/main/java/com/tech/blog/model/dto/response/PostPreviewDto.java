package com.tech.blog.model.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class PostPreviewDto {
    private Long id;
    private String title;
    private String excerpt;
    private String coverImageUrl;
    private String authorName;
    private LocalDateTime publishedAt;
    private Integer viewsCount;
    private Integer likesCount;
    private Integer commentsCount;
    private Set<String> tags;
    private String categoryName;
}
