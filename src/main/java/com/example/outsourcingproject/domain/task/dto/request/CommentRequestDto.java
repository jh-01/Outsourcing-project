package com.example.outsourcingproject.domain.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {

    private final String content;

    public CommentRequestDto(@NotBlank String content) {
        this.content = content;
    }
}
