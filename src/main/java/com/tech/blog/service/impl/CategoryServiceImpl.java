package com.tech.blog.service.impl;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.mapper.CategoryMapper;
import com.tech.blog.model.dto.request.CategoryCreateRequest;
import com.tech.blog.model.dto.request.CategoryUpdateRequest;
import com.tech.blog.model.dto.response.CategoryResponse;
import com.tech.blog.model.entity.Category;
import com.tech.blog.repository.CategoryRepository;
import com.tech.blog.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;




    @Override
    public CategoryResponse save(CategoryCreateRequest request) {
        log.info("Creating category Name: {}", request.getName());

        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category saved successfully with id: {}", savedCategory.getId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category not found with id: " +id));
        categoryRepository.delete(category);
        log.info("Category deleted successfully");
    }

    @Override
    public List<CategoryResponse> fetchAll() {
        log.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return null;
    }
}
