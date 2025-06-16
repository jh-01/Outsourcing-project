package com.example.outsourcingproject.global.exception;

import com.example.outsourcingproject.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleException(CustomException customException) {
        ApiResponse<?> response = ApiResponse.createError(customException);
        return ResponseEntity
                .status(customException.getErrorType().getHttpStatus())
                .body(response);
    }
}

