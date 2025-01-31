package com.tech.blog.model.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaseResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
}