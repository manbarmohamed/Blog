package com.tech.blog.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDetailResponse extends CategoryResponse {
    private List<PostSummaryResponse> recentPosts;
}