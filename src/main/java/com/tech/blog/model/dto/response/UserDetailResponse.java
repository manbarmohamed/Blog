package com.tech.blog.model.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class UserDetailResponse extends BaseResponse{
    private Integer postsCount;
    private Integer commentsCount;
    private Integer likesCount;
    private List<PostSummaryResponse> recentPosts;
    private List<CommentSummaryResponse> recentComments;
    private String profilePictureUrl;
}
