package com.tech.blog.controller;


import com.tech.blog.model.dto.request.TagCreateRequest;
import com.tech.blog.model.dto.response.TagResponse;
import com.tech.blog.service.interfaces.TagService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Management", description = "APIs for managing tags")
@Slf4j
public class TagController {

    private final TagService tagService;

    @Operation(
            summary = "Create a new tag",
            description = "Creates a new tag with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tag created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Tag with same name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagCreateRequest request) {
        log.info("REST request to create Tag");
        TagResponse response = tagService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/tags/" + response.getId()))
                .body(response);
    }

    @Operation(
            summary = "Delete a tag",
            description = "Deletes a tag with the provided ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Tag deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        log.info("REST request to delete Tag: {}", id);
        tagService.delete(id);
    }

    @Operation(
            summary = "Find a tag by ID",
            description = "Finds a tag with the provided ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tag found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public TagResponse findTagById(@PathVariable Long id) {
        log.info("REST request to find Tag: {}", id);
        return tagService.findById(id);
    }

    @Operation(
            summary = "Find all tags",
            description = "Finds all tags"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tags found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagResponse.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<TagResponse>> findAllTags() {
        log.info("REST request to find all Tags");
        return ResponseEntity.ok(tagService.fetchAll());
    }

}
