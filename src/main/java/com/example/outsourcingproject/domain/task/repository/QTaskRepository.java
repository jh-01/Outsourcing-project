package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QTaskRepository {
    TaskResponse searchTask(Long id);

    List<TaskResponse> searchTasks(TaskReadRequest request);
}
