package com.tech.blog.exception;

public class BlogApiException extends RuntimeException {
    public BlogApiException(String message) {
        super(message);
    }
}
