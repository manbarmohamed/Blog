package com.tech.blog.model.dto.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String name;
    private String description;
}
