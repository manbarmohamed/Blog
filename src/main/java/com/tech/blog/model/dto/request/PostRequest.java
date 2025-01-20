package com.tech.blog.model.dto.request;

import com.tech.blog.model.entity.Post;
import com.tech.blog.model.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Post}
 */
@Getter
@Setter
@AllArgsConstructor
public class PostRequest  {
    String title;
    String content;
    String imageUrl;
    PostStatus status;
    int views;
    Long user_id;
    Long category_id;
    List<Long> tag_ids;

}