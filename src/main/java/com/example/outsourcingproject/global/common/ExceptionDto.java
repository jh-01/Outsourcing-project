package com.example.outsourcingproject.global.common;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionDto {
    @NotNull
    private final String message;

}
