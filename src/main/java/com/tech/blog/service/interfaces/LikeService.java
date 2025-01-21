package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.request.LikeRequest;
import com.tech.blog.model.dto.response.LikeResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LikeService {
    LikeResponse toggleLike(@NotNull Long userId, @Valid @NotNull LikeRequest request);
    boolean hasUserLiked(@NotNull Long userId, @NotNull Long postId);
    long getLikeCount(@NotNull Long postId);
}