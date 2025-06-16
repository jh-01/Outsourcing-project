package com.example.outsourcingproject.domain.common.dto;

import com.example.outsourcingproject.domain.common.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

public record ApiResponse<T> (
    @JsonIgnore
    boolean success,
    String message,
    @Nullable T data,
    LocalDateTime timestamp
){
    public static <T> ApiResponse<T> ok(String message, @Nullable final T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> created(String message, @Nullable final T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(final CustomException e) {
        return new ApiResponse<>(false, e.getMessage(), null, LocalDateTime.now());
    }
}
