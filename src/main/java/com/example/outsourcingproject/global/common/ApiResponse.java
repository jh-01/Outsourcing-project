package com.example.outsourcingproject.global.common;

import com.example.outsourcingproject.domain.dashboard.dto.DashboardResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private boolean status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> createSuccess(String message, T data){
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static ApiResponse<?> createSuccessWithNoContent(String message) {
        return new ApiResponse<>(true, message,null, LocalDateTime.now());
    }

    // 커스텀 예외 발생
    public static ApiResponse<ExceptionDto> createError(CustomException e) {
        return new ApiResponse<>(false, e.getErrorType().getErrorMessage(), null, LocalDateTime.now());
    }

    // Validation 예외 발생
    public static ApiResponse<String> createValidationError(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }

    private ApiResponse(boolean status, String message,  T data, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> ok(String message, @Nullable final T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> created(String message, @Nullable final T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(final com.example.outsourcingproject.domain.common.exception.CustomException e) {
        return new ApiResponse<>(false, e.getMessage(), null, LocalDateTime.now());
    }
}
