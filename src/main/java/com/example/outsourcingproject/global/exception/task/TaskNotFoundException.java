package com.example.outsourcingproject.global.exception.task;

import lombok.Getter;

@Getter
public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
