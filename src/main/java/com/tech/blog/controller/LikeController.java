package com.tech.blog.controller;

import com.tech.blog.model.dto.request.LikeRequest;
import com.tech.blog.model.dto.response.LikeResponse;
import com.tech.blog.service.interfaces.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@Tag(name = "Like Management", description = "Endpoints for managing post likes")
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "Toggle like on a post",
            description = "Adds or removes a like from a post for the given user")
    @ApiResponse(responseCode = "200", description = "Like added successfully")
    @ApiResponse(responseCode = "204", description = "Like removed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<?> toggleLike(
            @RequestParam("userId") Long userId,
            @Valid @RequestBody LikeRequest likeRequest) {
        log.debug("Request to toggle like for user {} on post {}",
                userId, likeRequest.getPostId());

        LikeResponse response = likeService.toggleLike(userId, likeRequest);
        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if user has liked a post")
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLikeStatus(
            @RequestParam Long userId,
            @Parameter(description = "ID of the post to check")
            @RequestParam Long postId) {
        log.debug("Checking like status for user {} on post {}",
                userId, postId);

        boolean hasLiked = likeService.hasUserLiked(userId, postId);
        return ResponseEntity.ok(hasLiked);
    }

    @Operation(summary = "Get total likes for a post")
    @GetMapping("/count")
    public ResponseEntity<Long> getLikeCount(
            @Parameter(description = "ID of the post to count likes for")
            @RequestParam Long postId) {
        log.debug("Getting like count for post {}", postId);

        long count = likeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }
}