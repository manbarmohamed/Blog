package com.tech.blog.mapper;

import com.tech.blog.model.entity.Category;
import com.tech.blog.model.dto.request.CategoryCreateRequest;
import com.tech.blog.model.dto.request.CategoryUpdateRequest;
import com.tech.blog.model.dto.response.CategoryResponse;
import com.tech.blog.model.dto.response.CategoryDetailResponse;
import org.mapstruct.*;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PostMapper.class})
public interface CategoryMapper {

    @Named("toCategoryEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "posts", ignore = true)
    Category toEntity(CategoryCreateRequest createRequest);

    @Named("toCategoryResponse")
    CategoryResponse toResponse(Category category);

    @Named("toCategoryDetailResponse")
    @Mapping(target = "postsCount", expression = "java(category.getPosts().size())")
    @Mapping(target = "recentPosts", expression = "java(category.getPosts().stream().limit(5).map(postMapper::toSummaryResponse).collect(java.util.stream.Collectors.toList()))")
    CategoryDetailResponse toDetailResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(CategoryUpdateRequest dto, @MappingTarget Category category);
}