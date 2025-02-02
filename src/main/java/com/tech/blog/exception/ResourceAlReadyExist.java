package com.tech.blog.exception;

public class ResourceAlReadyExist extends RuntimeException {
    public ResourceAlReadyExist(String message) {
        super(message);
    }

    public ResourceAlReadyExist(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
