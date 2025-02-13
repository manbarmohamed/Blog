package com.tech.blog.controller;

import com.tech.blog.model.dto.response.LikeResponse;
import com.tech.blog.security.CustomUserDetails;
import com.tech.blog.service.interfaces.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like Management", description = "Endpoints for managing post likes")
@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "Toggle like status for a post")
    @ApiResponse(responseCode = "200", description = "Like status successfully toggled")
    @PostMapping("/posts/{postId}/toggle")
    public ResponseEntity<LikeResponse> toggleLike(
            @Parameter(description = "Post ID") @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        LikeResponse response = likeService.toggleLike(userDetails.getUserId(), postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get like status and count for a post")
    @ApiResponse(responseCode = "200", description = "Like information retrieved successfully")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<LikeResponse> getLikeInfo(
            @Parameter(description = "Post ID") @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        LikeResponse response = likeService.getLikeInfo(userDetails.getUserId(), postId);
        return ResponseEntity.ok(response);
    }
}