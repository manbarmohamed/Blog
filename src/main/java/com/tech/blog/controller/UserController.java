package com.tech.blog.controller;

import com.tech.blog.model.dto.request.UserUpdateRequest;
import com.tech.blog.model.dto.response.UserResponse;

import com.tech.blog.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing user profiles")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Update User Profile", description = "Updates the profile information of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or user data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest profileEditDTO) throws IOException {

        UserResponse user = userService.editUserProfile(userId, profileEditDTO);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}/profile-picture")
    @Operation(
            summary = "Update profile picture",
            description = "Upload a new profile picture for the user.",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "object", implementation = MultipartFile.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile picture updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file format"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<UserResponse> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) throws IOException {

        UserResponse updatedUser = userService.updateProfilePicture(userId, file);
        return ResponseEntity.ok(updatedUser);
    }


    @Operation(summary = "Get All Users", description = "Retrieves all users from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all users successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
