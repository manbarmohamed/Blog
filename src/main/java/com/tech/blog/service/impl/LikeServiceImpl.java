package com.tech.blog.service.impl;

import com.tech.blog.exception.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public LikeResponse toggleLike(Long userId, Long postId) {
        User user = findUserById(userId);
        Post post = findPostById(postId);

        var existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
        boolean hasLiked;
        String message;

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            hasLiked = false;
            message = "Post unliked successfully";
            log.info("User {} unliked post {}", userId, postId);
        } else {
            Like newLike = createNewLike(user, post);
            likeRepository.save(newLike);
            hasLiked = true;
            message = "Post liked successfully";
            log.info("User {} liked post {}", userId, postId);
        }

        long likeCount = likeRepository.countByPostId(postId);
        return buildLikeResponse(likeCount, hasLiked, message);
    }

    @Override
    public LikeResponse getLikeInfo(Long userId, Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        boolean hasLiked = likeRepository.existsByUserIdAndPostId(userId, postId);
        long likeCount = likeRepository.countByPostId(postId);

        return buildLikeResponse(likeCount, hasLiked, "Like information retrieved successfully");
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    private Like createNewLike(User user, Post post) {
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());
        return like;
    }

    private LikeResponse buildLikeResponse(long likeCount, boolean hasLiked, String message) {
        return LikeResponse.builder()
                .likeCount(likeCount)
                .hasUserLiked(hasLiked)
                .message(message)
                .build();
    }
}