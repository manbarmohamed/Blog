package com.tech.blog.service.impl;

import com.tech.blog.exception.BlogApiException;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.mapper.LikeMapper;
import com.tech.blog.model.dto.request.LikeRequest;
import com.tech.blog.model.dto.response.LikeResponse;
import com.tech.blog.model.entity.Like;
import com.tech.blog.model.entity.Post;
import com.tech.blog.model.entity.User;
import com.tech.blog.repository.LikeRepository;
import com.tech.blog.repository.PostRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.interfaces.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeMapper likeMapper;

    @Override
    @Transactional
    public LikeResponse toggleLike(Long userId, LikeRequest request) {
        log.debug("Toggling like for user {} on post {}", userId, request.getPostId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", request.getPostId()));

        try {
            var existingLike = likeRepository.findByUserIdAndPostId(userId, request.getPostId());

            if (existingLike.isPresent()) {
                likeRepository.delete(existingLike.get());
                log.debug("Deleted like for user {} on post {}", userId, request.getPostId());
                return null;
            }

            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            newLike.setCreatedAt(LocalDateTime.now());

            Like savedLike = likeRepository.save(newLike);
            log.debug("Created new like {} for user {} on post {}", savedLike.getId(), userId, request.getPostId());
            return likeMapper.toResponse(savedLike);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to toggle like for user {} on post {}", userId, request.getPostId(), e);
            throw new BlogApiException("Failed to process like operation");
        }
    }

    @Override
    public boolean hasUserLiked(Long userId, Long postId) {
        log.debug("Checking if user {} has liked post {}", userId, postId);
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public long getLikeCount(Long postId) {
        log.debug("Getting like count for post {}", postId);
        return likeRepository.countByPostId(postId);
    }
}