package com.tech.blog.service.impl;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.model.dto.request.*;
import com.tech.blog.model.dto.response.*;
import com.tech.blog.model.entity.*;
import com.tech.blog.repository.*;
import com.tech.blog.mapper.PostMapper;
import com.tech.blog.service.interfaces.PostService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        Post post = postMapper.toEntity(request);

        User author = userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", 1L));
        post.setUser(author);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
        post.setCategory(category);

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            post.setTags(new ArrayList<>(tags));
        }

        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postMapper.updatePostFromDto(request, post);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            post.setCategory(category);
        }

        if (request.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            post.setTags(tags);
        }

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.incrementViews(id);
        return postMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostPreviewDto> getPublishedPosts(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.findAllPublishedPosts(pageable);

        List<PostPreviewDto> content = postPage.getContent()
                .stream()
                .map(postMapper::toPreviewDto)
                .toList();

        return PageResponse.<PostPreviewDto>builder()
                .content(content)
                .pageNo(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostSummaryResponse> getAllPosts(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostSummaryResponse> content = postPage.getContent()
                .stream()
                .map(postMapper::toSummaryResponse)
                .toList();

        return PageResponse.<PostSummaryResponse>builder()
                .content(content)
                .pageNo(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        if (post.getCoverImageUrl() != null) {
            String publicId = extractPublicIdFromUrl(post.getCoverImageUrl());
            try {
                imageService.deleteImage(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image", e);
            }
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostStatusResponse updatePostStatus(Long id, PostStatusRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setStatus(request.getStatus());
        Post updatedPost = postRepository.save(post);

        return PostStatusResponse.builder()
                .id(updatedPost.getId())
                .status(updatedPost.getStatus())
                .updatedAt(updatedPost.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public PostResponse updatePostImage(Long id, String imageUrl) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        if (post.getCoverImageUrl() != null) {
            String publicId = extractPublicIdFromUrl(post.getCoverImageUrl());
            try {
                imageService.deleteImage(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete old image", e);
            }
        }

        post.setCoverImageUrl(imageUrl);
        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Long categoryId) {
        List<Post> posts = postRepository.findByCategory_Id(categoryId);
        return posts.stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    public List<PostResponse> getPostsByTag(String tagName) {
        List<Post> posts = postRepository.findPostsByTagName(tagName);
        return posts.stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByTagId(Long tagId) {
        List<Post> posts = postRepository.findPostsByTagId(tagId);
        return posts.stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }


    private String extractPublicIdFromUrl(String url) {
        if (url == null) return null;
        String[] parts = url.split("/");
        return parts[parts.length - 1].split("\\.")[0];
    }
}