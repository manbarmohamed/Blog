package com.tech.blog.model.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LikeResponse extends BaseResponse {
    private Long id;
    private LocalDateTime createdAt;
    private UserSummaryResponse user;
    private PostSummaryResponse post;
}
