package com.tech.blog.service.interfaces;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.model.dto.request.TagCreateRequest;
import com.tech.blog.model.dto.response.TagResponse;

import java.util.List;

public interface TagService {


    /**
     * Create a new tag
     * @param request The tag creation request
     * @return The created tag response
     */
    TagResponse save(TagCreateRequest request);

    /**
     * Find all tags
     * @return List of all tag responses
     */
    List<TagResponse> fetchAll();

    /**
     * Find a tag by its ID
     * @param id The tag ID to find
     * @return The found tag response
     * @throws ResourceNotFoundException if tag not found
     */
    TagResponse findById(Long id);

    /**
     * Delete a tag by its ID
     * @param id The tag ID to delete
     */
    void delete(Long id);


    
}
