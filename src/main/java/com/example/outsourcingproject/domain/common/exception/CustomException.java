package com.example.outsourcingproject.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final String message;

    public String getMessagge(){
        return message;
    }
}
