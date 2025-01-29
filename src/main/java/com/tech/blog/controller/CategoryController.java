package com.tech.blog.controller;


import com.tech.blog.model.dto.request.CategoryCreateRequest;
import com.tech.blog.model.dto.request.CategoryUpdateRequest;
import com.tech.blog.model.dto.response.CategoryResponse;
import com.tech.blog.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Category Management", description = "APIs for managing categories")

public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<CategoryResponse> save
            (@Parameter(description = "Category creation request", required = true)
             @RequestBody @Valid
             CategoryCreateRequest request)
    {
        log.info("REST request to create Category");
        CategoryResponse response = categoryService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/categories/save" + response.getId()))
                .body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update an existing category",
            description = "Updates a category based on the provided ID and details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<CategoryResponse> update(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Category update request", required = true)
            @RequestBody @Valid CategoryUpdateRequest request) {

        log.info("REST request to update Category : {}", id);

        CategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findById/{id}")
    @Operation(
            summary = "Find category by ID",
            description = "Retrieves a specific category based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<CategoryResponse> findById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable("id") Long id) {

        log.info("REST request to get Category : {}", id);

        CategoryResponse response = categoryService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a category",
            description = "Deletes a category based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    public void delete(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {

        log.info("REST request to delete Category : {}", id);
        categoryService.delete(id);
    }

    @GetMapping
    @Operation(
            summary = "Fetch all categories",
            description = "Retrieves a list of all categories"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved categories",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))
            )
    )
    public ResponseEntity<List<CategoryResponse>> fetchAll() {

        log.info("REST request to get all Categories");
        return ResponseEntity.ok().body(categoryService.fetchAll());
    }
}
