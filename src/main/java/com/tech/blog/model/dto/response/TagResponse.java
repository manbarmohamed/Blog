package com.tech.blog.model.dto.response;

import lombok.Data;

@Data
public class TagResponse extends BaseResponse {
    private Long id;
    private String name;
    //private Integer postsCount;
}