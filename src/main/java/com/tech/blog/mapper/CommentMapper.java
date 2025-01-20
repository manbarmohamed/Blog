package com.tech.blog.mapper;

import com.tech.blog.model.entity.Comment;
import com.tech.blog.model.dto.request.CommentCreateRequest;
import com.tech.blog.model.dto.request.CommentUpdateRequest;
import com.tech.blog.model.dto.response.CommentResponse;
import com.tech.blog.model.dto.response.CommentSummaryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, PostMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toEntity(CommentCreateRequest createRequest);

    @Mapping(target = "author", source = "user")
    CommentResponse toResponse(Comment comment);

    CommentSummaryResponse toSummaryResponse(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(CommentUpdateRequest dto, @MappingTarget Comment comment);
}