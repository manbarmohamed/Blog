package com.tech.blog.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TagDetailResponse extends TagResponse {
    private List<PostSummaryResponse> recentPosts;
}