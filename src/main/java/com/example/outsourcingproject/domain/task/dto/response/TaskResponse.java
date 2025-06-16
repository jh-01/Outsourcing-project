package com.example.outsourcingproject.domain.task.dto.response;

import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TaskResponse {

    private final String title;
    private final String description;
    private final Priority priority;
    private final String managerName;
    private final String generatorName;
    private final LocalDateTime deadline;
    private final Status status;
    private final LocalDateTime startAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    public TaskResponse(String title, String description, Priority priority, String managerName, String generatorName, LocalDateTime deadline, Status status,LocalDateTime startAt, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.managerName = managerName;
        this.generatorName = generatorName;
        this.deadline = deadline;
        this.status = status;
        this.startAt = startAt;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
