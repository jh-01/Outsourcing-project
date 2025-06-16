package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class TaskReadRequest {
    private int page = 0;
    private int size = 10;

    private String title;
    private String description;
    private Status status;
}
