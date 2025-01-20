package com.tech.blog.controller;


import com.tech.blog.model.dto.request.CategoryCreateRequest;
import com.tech.blog.model.dto.request.CategoryUpdateRequest;
import com.tech.blog.model.dto.response.CategoryResponse;
import com.tech.blog.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> save
            (@RequestBody @Valid
             CategoryCreateRequest request) {
        log.info("REST request to create Category");
        CategoryResponse response = categoryService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/categories/save" + response.getId()))
                .body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid CategoryUpdateRequest request) {
        log.info("REST request to update Category : {}", id);
        CategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        log.info("REST request to get Category : {}", id);
        CategoryResponse response = categoryService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("REST request to delete Category : {}", id);
        categoryService.delete(id);
    }
}
