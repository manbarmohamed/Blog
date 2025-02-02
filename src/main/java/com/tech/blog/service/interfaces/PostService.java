package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.request.PostCreateRequest;
import com.tech.blog.model.dto.request.PostStatusRequest;
import com.tech.blog.model.dto.request.PostUpdateRequest;
import com.tech.blog.model.dto.response.PageResponse;
import com.tech.blog.model.dto.response.PostResponse;
import com.tech.blog.model.dto.response.PostStatusResponse;
import com.tech.blog.model.dto.response.PostSummaryResponse;
import com.tech.blog.model.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostCreateRequest request);
    PostResponse updatePost(Long id, PostUpdateRequest request);
    PostResponse getPostById(Long id);
    PageResponse<PostSummaryResponse> getAllPosts(int page, int size, String sortBy, String direction);
    void deletePost(Long id);
    PostStatusResponse updatePostStatus(Long id, PostStatusRequest request);
    PostResponse updatePostImage(Long id, String imageUrl);

    List<PostResponse> getPostsByCategory(Long categoryId);

    List<PostResponse> getPostsByTag(String tagName);
    List<PostResponse> getPostsByTagId(Long tagId);
}