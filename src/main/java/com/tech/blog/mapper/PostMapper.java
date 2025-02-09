package com.tech.blog.mapper;

import com.tech.blog.model.dto.request.PostCreateRequest;
import com.tech.blog.model.dto.request.PostUpdateRequest;
import com.tech.blog.model.dto.response.PostPreviewDto;
import com.tech.blog.model.dto.response.PostResponse;
import com.tech.blog.model.dto.response.PostSummaryResponse;
import com.tech.blog.model.entity.Post;
import com.tech.blog.model.entity.Tag;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, CategoryMapper.class, TagMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "views", constant = "0")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Post toEntity(PostCreateRequest createRequest);

    @Mapping(target = "author", source = "user")
    @Mapping(target = "commentsCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    PostResponse toResponse(Post post);

    @Mapping(target = "coverImageUrl", source = "coverImageUrl")
    @Mapping(target = "publishedAt", source = "createdAt")
    @Mapping(target = "viewsCount", source = "views")
    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    @Mapping(target = "commentsCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "authorName", source = "user.username")
    @Mapping(target = "excerpt", expression = "java(generateExcerpt(post.getContent()))")
    @Mapping(target = "tags", source = "tags")
    PostPreviewDto toPreviewDto(Post post);

    @Mapping(target = "commentsCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    PostSummaryResponse toSummaryResponse(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostUpdateRequest dto, @MappingTarget Post post);

    default String generateExcerpt(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        int end = Math.min(content.length(), 200);
        return content.substring(0, end) + (content.length() > 200 ? "..." : "");
    }

    default Set<String> map(List<Tag> tags) {
        return tags != null
                ? tags.stream().map(Tag::getName).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}