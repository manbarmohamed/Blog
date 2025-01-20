package com.tech.blog.mapper;

import com.tech.blog.model.entity.Tag;
import com.tech.blog.model.dto.request.TagCreateRequest;
import com.tech.blog.model.dto.response.TagResponse;
import org.mapstruct.*;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PostMapper.class})
public interface TagMapper {

    @Named("toTagEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    Tag toEntity(TagCreateRequest createRequest);

    @Named("toTagResponse")
    @Mapping(target = "postsCount", expression = "java(tag.getPosts().size())")
    TagResponse toResponse(Tag tag);

//    @Named("toTagDetailResponse")
//    @Mapping(target = "postsCount", expression = "java(tag.getPosts().size())")
//    @Mapping(target = "recentPosts", expression = "java(tag.getPosts().stream().limit(5).map(postMapper::toSummaryResponse).collect(java.util.stream.Collectors.toList()))")
//    TagDetailResponse toDetailResponse(Tag tag);
}