package com.example.outsourcingproject.domain.task.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String contents;

    public CommentRequestDto(String contents) {
        this.contents = contents;
    }
}
