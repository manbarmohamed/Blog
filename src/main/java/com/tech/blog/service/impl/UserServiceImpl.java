package com.tech.blog.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tech.blog.mapper.UserMapper;
import com.tech.blog.model.dto.request.UserUpdateRequest;
import com.tech.blog.model.dto.response.UserResponse;
import com.tech.blog.model.entity.User;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Cloudinary cloudinary;


    @Override
    public UserResponse editUserProfile(Long userId, UserUpdateRequest userUpdateRequest) {

        log.info("Editing user profile with id: {}", userId);

        User user = userRepository.findById(userId).
                orElseThrow(
                        () -> new RuntimeException("User not found")
                );
        userMapper.updateUserFromDto(userUpdateRequest, user);

        log.info("User profile updated successfully");

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfilePicture(Long userId, MultipartFile file) throws IOException {

        log.info("Updating profile picture for user with id: {}", userId);

        User user = userRepository.findById(userId).
                orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        log.info("Uploading image to cloudinary");

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        log.info("Image uploaded successfully");

        user.setProfilePictureUrl(imageUrl);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }
}
