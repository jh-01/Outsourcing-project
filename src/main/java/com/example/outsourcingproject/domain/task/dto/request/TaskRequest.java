package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Priority;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskRequest {

    @NotNull
    private String title;
    private String description;

    @NotNull
    private Priority priority;

    private Long managerId;
    private LocalDateTime deadline;

}
