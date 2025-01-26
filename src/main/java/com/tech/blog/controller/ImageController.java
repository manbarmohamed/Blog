package com.tech.blog.controller;

import com.tech.blog.model.dto.response.PostResponse;
import com.tech.blog.service.impl.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
@Tag(name = "Image Management", description = "APIs for image upload and management")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
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
                            content = @Content(schema = @Schema(implementation = com.tech.blog.model.dto.response.ErrorResponse.class)),
                            headers = {@io.swagger.v3.oas.annotations.headers.Header(
                                    name = "Location",
                                    description = "URL of the uploaded image",
                                    schema = @Schema(type = "string")
                            )}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Optional<String>> uploadImage(
            @Parameter(
                    description = "Image file to upload",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");
            }

            String contentType = file.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Only JPEG/PNG images are allowed");
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "File size exceeds 5MB limit");
            }

            Optional<String> uploadResult = imageService.uploadImage(file);
            return ResponseEntity.ok(uploadResult);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to upload image: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Delete image",
            description = "Delete an image by its public ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Image deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to delete image",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteImage(
            @Parameter(
                    description = "Cloudinary public ID of the image",
                    example = "sample_public_id"
            )
            @RequestParam("public_id") String publicId) {
        try {
            imageService.deleteImage(publicId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete image: " + e.getMessage());
        }
    }
}