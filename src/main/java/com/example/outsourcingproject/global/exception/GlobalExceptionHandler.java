package com.example.outsourcingproject.global.exception;

import com.example.outsourcingproject.global.exception.comments.CommentErrorDto;
import com.example.outsourcingproject.global.exception.comments.CommentNotFound;
import com.example.outsourcingproject.global.exception.comments.TaskNotFound;
import com.example.outsourcingproject.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(CommentNotFound.class)
    public ResponseEntity<CommentErrorDto> CommentNotFoundExp(CommentNotFound e) {
        Errorcode errorcode = e.getErrorcode();
        return ResponseEntity
                .status(errorcode.getStatus())
                .body(new CommentErrorDto(e.getErrorcode().COMMENT_NOT_FOUND.getMessages()));
    }

    @ExceptionHandler
    public ResponseEntity<CommentErrorDto> TaskNotFoundExp(TaskNotFound e) {
        Errorcode errorcode = e.getErrorcode();
        return ResponseEntity
                .status(errorcode.getStatus())
                .body(new CommentErrorDto(e.getErrorcode().TASK_NOT_FOUND.getMessages()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<?> response = ApiResponse.createValidationError(message);
        return ResponseEntity.badRequest().body(response);
    }
}

