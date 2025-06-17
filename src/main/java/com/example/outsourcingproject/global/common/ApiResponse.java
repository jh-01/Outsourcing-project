package com.example.outsourcingproject.global.common;

import com.example.outsourcingproject.global.exception.CustomException;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> createSuccess(String message, @Nullable T data){
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    // 커스텀 예외 발생
    public static <T >ApiResponse<T> createError(CustomException e) {
        return new ApiResponse<>(false, e.getErrorType().getErrorMessage(), null, LocalDateTime.now());
    }

    // Validation 예외 발생
    public static <T> ApiResponse<T> createValidationError(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }

    private ApiResponse(boolean success, String message,  T data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
}
