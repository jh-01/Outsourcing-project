package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(max = 30, message = "제목은 최대 30글자 입니다.")
    private String title;
    @Size(max = 255, message = "내용은 최대 255글자 입니다.")
    private String description;

    // TODO : HttpMessageNotReadableException?
    @NotNull(message = "우선순위는 필수 항목입니다.")
    private Priority priority;

    @NotNull(message = "담당자는 필수 항목입니다.")
    private Long managerId;
    private LocalDateTime deadline;

}
