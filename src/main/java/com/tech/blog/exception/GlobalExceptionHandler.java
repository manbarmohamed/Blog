package com.tech.blog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        ApiError error = new ApiError();
        error.setMessage("Resource not found");
        error.setDetails(ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ApiError error = new ApiError();
        error.setMessage("Validation failed");
        error.setDetails(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage("An unexpected error occurred");
        error.setDetails(ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}