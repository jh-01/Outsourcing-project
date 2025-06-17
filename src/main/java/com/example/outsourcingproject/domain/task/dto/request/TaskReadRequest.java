package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskReadRequest {
    private int page = 0;
    private int size = 10;

    private String title;
    private String description;
    private Status status;
    private Long managerId;

    public TaskReadRequest(Long managerId) {
        this.title = null;
        this.description = null;
        this.status = null;
        this.managerId = managerId;
    }
}
