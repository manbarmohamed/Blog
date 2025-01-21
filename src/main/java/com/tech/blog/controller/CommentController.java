package com.tech.blog.controller;

import com.tech.blog.model.dto.request.CommentCreateRequest;
import com.tech.blog.model.dto.request.CommentUpdateRequest;
import com.tech.blog.model.dto.response.CommentResponse;
import com.tech.blog.service.interfaces.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Management", description = "Endpoints for managing comments on posts")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Create a comment on a post",
            description = "Creates a new comment on a post for the given user")
    @ApiResponse(responseCode = "201", description = "Comment created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @RequestParam("userId") Long userId,
            @Valid @RequestBody CommentCreateRequest commentRequest) {
        log.debug("Request to create comment for user {} on post {}",
                userId, commentRequest.getPostId());

        CommentResponse response = commentService.createComment(userId, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a comment by ID")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(
            @Parameter(description = "ID of the comment to fetch")
            @PathVariable Long commentId) {
        log.debug("Fetching comment with id {}", commentId);

        CommentResponse response = commentService.getComment(commentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a comment by ID")
    @DeleteMapping("/{commentId}")
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID of the comment to delete")
            @PathVariable Long commentId) {
        log.debug("Deleting comment with id {}", commentId);

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all comments for a post")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(
            @Parameter(description = "ID of the post to fetch comments for")
            @PathVariable Long postId) {
        log.debug("Fetching comments for post with id {}", postId);

        List<CommentResponse> responses = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update a comment by ID")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @Parameter(description = "ID of the comment to update")
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        log.debug("Updating comment with id {}", commentId);

        CommentResponse response = commentService.updateComment(commentId, commentUpdateRequest);
        return ResponseEntity.ok(response);
    }
}