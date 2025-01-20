package com.tech.blog.model.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private String title;
    private String content;
    private String imageUrl;
    private Long categoryId;
    private List<Long> tagIds;
}
