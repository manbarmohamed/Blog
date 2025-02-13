package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.response.LikeResponse;

public interface LikeService {
    /**
     * Toggles like status and returns updated like information
     *
     * @param userId ID of the user performing the action
     * @param postId ID of the post being liked/unliked
     * @return LikeResponse containing updated like status and count
     */
    LikeResponse toggleLike(Long userId, Long postId);

    /**
     * Gets like information for a post
     *
     * @param userId ID of the user requesting information
     * @param postId ID of the post
     * @return LikeResponse containing like status and count
     */
    LikeResponse getLikeInfo(Long userId, Long postId);
}