package com.tech.blog.controller;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.model.dto.request.*;
import com.tech.blog.model.dto.response.*;
import com.tech.blog.service.impl.ImageService;
import com.tech.blog.service.interfaces.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Post Management", description = "APIs for managing blog posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ImageService imageService;

    @Operation(
            summary = "Create a New Blog Post",
            description = "Endpoint for creating a new blog post with comprehensive details. " +
                    "Requires a valid post creation request with title, content, category, and optional tags.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post successfully created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation Error - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Title Validation Error",
                                                    value = "{\"message\": \"Title must be between 3 and 255 characters\", \"status\": 400}"
                                            ),
                                            @ExampleObject(
                                                    name = "Content Validation Error",
                                                    value = "{\"message\": \"Content cannot be empty\", \"status\": 400}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - Insufficient permissions",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource Not Found - Category or Tags not exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(
            @Parameter(
                    name = "postCreateRequest",
                    description = "Post creation request payload",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostCreateRequest.class)
                    )
            )
            @Valid
            @RequestBody
            PostCreateRequest request
    ) {
        try {
            return postService.createPost(request);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Referenced category or tags not found",
                    ex
            );
        } catch (ValidationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Validation failed: " + ex.getMessage(),
                    ex
            );
        }
    }

    @Operation(
            summary = "Update post",
            description = "Update an existing blog post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @PutMapping("/{id}")
    public PostResponse updatePost(
            @Parameter(description = "ID of the post to update", example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody PostUpdateRequest request) {
        return postService.updatePost(id, request);
    }

    @Operation(
            summary = "Get post by ID",
            description = "Retrieve a single post with full details and increment view count",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post found"),
                    @ApiResponse(responseCode = "404", description = "Post not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    public PostResponse getPostById(
            @Parameter(description = "ID of the post to retrieve", example = "1")
            @PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @Operation(
            summary = "Get all published posts",
            description = "Retrieve a paginated list of all published posts with sorting options.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Published posts retrieved successfully",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/published")
    public PageResponse<PostPreviewDto> findAllPublishedPosts(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "Sort field",
                    schema = @Schema(allowableValues = {"createdAt", "title", "views"}),
                    example = "createdAt")
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

        return postService.getPublishedPosts(page, size, sortBy);
    }

    @Operation(
            summary = "Get all posts",
            description = "Get paginated list of posts with sorting options",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
            }
    )
    @GetMapping
    public PageResponse<PostSummaryResponse> getAllPosts(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "Sort field",
                    schema = @Schema(allowableValues = {"createdAt", "title", "views"}),
                    example = "createdAt")
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,

            @Parameter(description = "Sort direction",
                    schema = @Schema(allowableValues = {"asc", "desc"}),
                    example = "desc")
            @RequestParam(name = "direction", defaultValue = "desc") String direction) { // Add name="direction"
        return postService.getAllPosts(page, size, sortBy, direction);
    }

    @Operation(
            summary = "Delete post",
            description = "Delete a post and its associated image",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Post not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(
            @Parameter(description = "ID of the post to delete", example = "1")
            @PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    @Operation(
            summary = "Update post status",
            description = "Change publication status of a post (DRAFT/PUBLISHED/ARCHIVED)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid status",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @PatchMapping("/{id}/status")
    public PostStatusResponse updatePostStatus(
            @Parameter(description = "ID of the post to update", example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody PostStatusRequest request) {
        return postService.updatePostStatus(id, request);
    }

    @Operation(
            summary = "Upload post image",
            description = "Upload a cover image for a post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image uploaded successfully",
                            content = @Content(schema = @Schema(implementation = PostResponse.class)),
                            headers = {@io.swagger.v3.oas.annotations.headers.Header(
                                    name = "Location",
                                    description = "URL of the uploaded image",
                                    schema = @Schema(type = "string")
                            )}
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                            headers = {@io.swagger.v3.oas.annotations.headers.Header(
                                    name = "Location",
                                    description = "URL of the uploaded image",
                                    schema = @Schema(type = "string")
                            )}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping(value = "/{id}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponse uploadImage(
            @Parameter(description = "ID of the post to update", example = "1")
            @PathVariable("id") Long id,

            @Parameter(
                    description = "Image file to upload (JPEG/PNG, max 5MB)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {

        validateFile(file);

        try {
            String imageUrl = imageService.uploadImage(file)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Image upload failed"
                    ));
            return postService.updatePostImage(id, imageUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to process image: " + e.getMessage(),
                    e
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "File cannot be empty");
        }

        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unsupported file type. Only JPEG/PNG allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "File size exceeds maximum limit of 5MB");
        }
    }

    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get posts by category",
            description = "Retrieve a list of posts that belong to a specific category by its ID."
    )
    public ResponseEntity<List<PostResponse>> getPostsByCategory(
            @Parameter(description = "ID of the category to retrieve posts from", example = "1")
            @PathVariable("categoryId") Long categoryId) {

        List<PostResponse> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Find posts by tag",
            description = "Retrieves all published posts that have been tagged with the specified tag name"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved posts",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid tag name supplied",
                    content = @Content
            )
    })
    @GetMapping("/by-tag/{tagName}")
    public ResponseEntity<List<PostResponse>> findPostsByTag(
            @Parameter(
                    description = "Name of the tag to filter posts by",
                    required = true,
                    example = "spring-boot"
            )
            @PathVariable("tagName") String tagName
    ) {
        List<PostResponse> posts = postService.getPostsByTag(tagName);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Find posts by tag ID",
            description = "Retrieves all published posts that have been tagged with the specified tag ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved posts",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid tag ID supplied",
                    content = @Content
            )
    })
    @GetMapping("/by-tag/{id}")
    public ResponseEntity<List<PostResponse>> findPostsByTag(
            @Parameter(
                    description = "ID of the tag to filter posts by",
                    required = true,
                    example = "1"
            )
            @PathVariable("id") Long id
    ) {
        List<PostResponse> posts = postService.getPostsByTagId(id);
        return ResponseEntity.ok(posts);
    }

}