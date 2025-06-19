package com.example.outsourcingproject.domain.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {

    private final String contents;

    public CommentRequestDto(@NotBlank String contents) {
        this.contents = contents;
    }

}
