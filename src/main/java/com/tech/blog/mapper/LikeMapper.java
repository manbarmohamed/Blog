package com.tech.blog.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.tech.blog.model.dto.request.LikeRequest;
import com.tech.blog.model.dto.response.LikeResponse;
import com.tech.blog.model.entity.Like;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, PostMapper.class})
public interface LikeMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Like toEntity(LikeRequest likeRequest);

    LikeResponse toResponse(Like like);
}