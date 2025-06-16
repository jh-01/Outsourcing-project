package com.example.outsourcingproject.global.exception;

import com.example.outsourcingproject.global.exception.comments.CommentNotFound;
import com.example.outsourcingproject.global.exception.comments.TaskNotFound;
import com.example.outsourcingproject.global.exception.entity.ErrorResponse;
import com.example.outsourcingproject.global.exception.log.LogNotFoundException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(CommentNotFound.class)
    public ResponseEntity<ErrorResponse> CommentNotFoundExp(CommentNotFound e) {
        Errorcode errorcode = e.getErrorcode();
        return ResponseEntity
                .status(errorcode.getStatus())
                .body(new ErrorResponse(e.getErrorcode().COMMENT_NOT_FOUND.getMessages()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> TaskNotFoundExp(TaskNotFound e) {
        Errorcode errorcode = e.getErrorcode();
        return ResponseEntity
                .status(errorcode.getStatus())
                .body(new ErrorResponse(e.getErrorcode().TASK_NOT_FOUND.getMessages()));
    }



}
