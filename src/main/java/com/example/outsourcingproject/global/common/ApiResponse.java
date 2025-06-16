package com.example.outsourcingproject.global.common;

import com.example.outsourcingproject.global.exception.CustomException;
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

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<ExceptionDto> createError(CustomException e) {
        return new ApiResponse<>(false, e.getErrorType().getErrorMessage(), null, LocalDateTime.now());
    }


    private ApiResponse(boolean status, String message,  T data, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }






}
