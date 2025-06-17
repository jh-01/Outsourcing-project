package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;

public class TaskNotFound extends CustomException {
    private ErrorType errorType;

    public TaskNotFound(ErrorType errorType) {
        super(errorType.TASK_NOT_FOUND);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
