package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.request.CommentCreateRequest;
import com.tech.blog.model.dto.request.CommentUpdateRequest;
import com.tech.blog.model.dto.response.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CommentService {
    CommentResponse createComment(@NotNull Long userId, @Valid @NotNull CommentCreateRequest request);
    CommentResponse getComment(@NotNull Long commentId);
    void deleteComment(@NotNull Long commentId);
    List<CommentResponse> getCommentsByPostId(@NotNull Long postId);
    CommentResponse updateComment(@NotNull Long commentId, @Valid @NotNull CommentUpdateRequest request);
}