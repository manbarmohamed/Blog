package com.tech.blog.service.impl;

import com.tech.blog.exception.BlogApiException;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.mapper.CommentMapper;
import com.tech.blog.model.dto.request.CommentCreateRequest;
import com.tech.blog.model.dto.request.CommentUpdateRequest;
import com.tech.blog.model.dto.response.CommentResponse;
import com.tech.blog.model.entity.Comment;
import com.tech.blog.model.entity.Post;
import com.tech.blog.model.entity.User;
import com.tech.blog.repository.CommentRepository;
import com.tech.blog.repository.PostRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse createComment(Long userId, CommentCreateRequest request) {
        log.debug("Creating comment for user {} on post {}", userId, request.getPostId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", request.getPostId()));

        try {
            Comment comment = commentMapper.toEntity(request);
            comment.setUser(user);
            comment.setPost(post);
            comment.setCreateAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());

            Comment savedComment = commentRepository.save(comment);
            log.debug("Created new comment {} for user {} on post {}", savedComment.getId(), userId, request.getPostId());
            return commentMapper.toResponse(savedComment);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create comment for user {} on post {}", userId, request.getPostId(), e);
            throw new BlogApiException("Failed to process comment creation");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getComment(Long commentId) {
        log.debug("Fetching comment with id {}", commentId);
        Comment comment = commentRepository.findByIdWithPostAndComments(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        return commentMapper.toResponse(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        log.debug("Deleting comment with id {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        log.debug("Fetching comments for post with id {}", postId);
        return commentRepository.findByPostId(postId).stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        log.debug("Updating comment with id {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toResponse(updatedComment);
    }
}