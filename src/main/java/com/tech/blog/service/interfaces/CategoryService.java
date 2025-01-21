package com.tech.blog.service.interfaces;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.model.dto.request.CategoryCreateRequest;
import com.tech.blog.model.dto.request.CategoryUpdateRequest;
import com.tech.blog.model.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {


    /**
     * Save a new category
     * @param request The category creation request
     * @return The saved category response
     */
    CategoryResponse save(CategoryCreateRequest request);


    /**
     * Update an existing category
     * @param id The category ID to update
     * @param request The category update request
     * @return The updated category response
     */
    CategoryResponse update(Long id, CategoryUpdateRequest request);


    /**
     * Delete a category by its ID
     * @param id The category ID to delete
     */
    void delete(Long id);


    /**
     * Fetch all categories
     * @return List of all category responses
     */
    List<CategoryResponse> fetchAll();


    /**
     * Find a category by its ID
     * @param id The category ID to find
     * @return The found category response
     * @throws ResourceNotFoundException if category not found
     */
    CategoryResponse findById(Long id);
}