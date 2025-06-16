package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.task.repository.QTaskRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.global.exception.task.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final QTaskRepository taskRepository;

    public List<TaskResponse> getTaskList(TaskReadRequest request){
        return taskRepository.searchTasks(request);
    }

    public TaskResponse getTask(Long id){
        TaskResponse response = taskRepository.searchTask(id);
        if(response == null){
            throw new TaskNotFoundException("존재하지 않는 작업입니다. id : " + id);
        }
        return response;
    }
}
