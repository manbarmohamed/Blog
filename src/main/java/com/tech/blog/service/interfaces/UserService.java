package com.tech.blog.service.interfaces;

import com.tech.blog.model.dto.request.UserUpdateRequest;
import com.tech.blog.model.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserResponse editUserProfile(Long userId, UserUpdateRequest userUpdateRequest);
    UserResponse updateProfilePicture(Long userId, MultipartFile file) throws IOException;
    List<UserResponse> getAllUsers();
}
