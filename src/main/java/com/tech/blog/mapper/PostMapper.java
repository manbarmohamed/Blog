package com.tech.blog.mapper;

import com.tech.blog.model.entity.Post;
import com.tech.blog.model.dto.request.PostCreateRequest;
import com.tech.blog.model.dto.request.PostUpdateRequest;
import com.tech.blog.model.dto.response.PostResponse;
import com.tech.blog.model.dto.response.PostSummaryResponse;
import com.tech.blog.model.enums.PostStatus;
import org.mapstruct.Named;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, CategoryMapper.class, TagMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "views", constant = "0")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Post toEntity(PostCreateRequest createRequest);

    @Mapping(target = "author", source = "user")
    @Mapping(target = "likesCount", expression = "java(post.getLikes().size())")
    @Mapping(target = "category", qualifiedBy = Named.class, qualifiedByName = "toCategoryResponse")
    @Mapping(target = "tags", qualifiedBy = Named.class, qualifiedByName = "toTagResponse")
    PostResponse toResponse(Post post);

    @Mapping(target = "commentsCount", expression = "java(post.getComments().size())")
    @Mapping(target = "likesCount", expression = "java(post.getLikes().size())")
    PostSummaryResponse toSummaryResponse(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostUpdateRequest dto, @MappingTarget Post post);
}