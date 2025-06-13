package com.example.outsourcingproject.global.exception;

import com.example.outsourcingproject.global.exception.entity.ErrorResponse;
import com.example.outsourcingproject.global.exception.log.LogNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LogNotFoundException.class)
    public ResponseEntity<ErrorResponse> logNotFoundExp(LogNotFoundException e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage()));
    }



}
