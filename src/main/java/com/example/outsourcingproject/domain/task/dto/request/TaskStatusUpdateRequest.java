package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TaskStatusUpdateRequest {
    @NotNull
    private Status status;
}
