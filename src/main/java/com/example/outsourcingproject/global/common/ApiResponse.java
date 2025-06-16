package com.example.outsourcingproject.global.common;

import com.example.outsourcingproject.global.exception.CustomException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "true";
    private static final String ERROR_STATUS = "false";

    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> createSuccess(String message, T data, LocalDateTime timestamp){
        return new ApiResponse<>(SUCCESS_STATUS, message, data, timestamp);
    }

    public static ApiResponse<?> createSuccessWithNoContent(String message, LocalDateTime timestamp) {
        return new ApiResponse<>(SUCCESS_STATUS, message,null, timestamp);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<ExceptionDto> createError(CustomException e, LocalDateTime timestamp) {
        return new ApiResponse<>(ERROR_STATUS, e.getErrorType().getErrorMessage(), null , timestamp);
    }


    private ApiResponse(String status, String message,  T data, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }






}
