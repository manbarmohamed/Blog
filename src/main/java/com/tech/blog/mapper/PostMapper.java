package com.tech.blog.mapper;

import com.tech.blog.model.dto.response.PostResponse;
import com.tech.blog.model.dto.request.PostRequest;
import com.tech.blog.model.entity.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {


    Post toEntity(PostRequest postRequest);

    PostRequest toRequestDto(Post post);

    Post toEntity(PostResponse postResponse);

    PostResponse toResponseDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post partialUpdate(PostRequest postRequest, @MappingTarget Post post);
}