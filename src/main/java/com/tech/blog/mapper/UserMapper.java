package com.tech.blog.mapper;

import com.tech.blog.model.entity.User;
import com.tech.blog.model.dto.request.RegisterRequest;
import com.tech.blog.model.dto.request.UserUpdateRequest;
import com.tech.blog.model.dto.response.UserResponse;
import com.tech.blog.model.dto.response.UserDetailResponse;
import com.tech.blog.model.dto.response.UserSummaryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    User toEntity(RegisterRequest registerRequest);

    @Mapping(target = "postsCount", expression = "java(user.getPosts().size())")
    @Mapping(target = "commentsCount", expression = "java(user.getComments().size())")
    @Mapping(target = "likesCount", expression = "java(user.getLikes().size())")
    UserDetailResponse toDetailResponse(User user);

    UserResponse toResponse(User user);
    
    UserSummaryResponse toSummaryResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequest dto, @MappingTarget User user);
}