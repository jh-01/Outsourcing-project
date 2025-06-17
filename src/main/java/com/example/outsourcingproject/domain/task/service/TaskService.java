package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskResponse> findTasks(TaskReadRequest request){
        return taskRepository.findTasks(request);
    }

    public TaskResponse findTask(Long id){
        TaskResponse response = taskRepository.findTaskById(id);
        if(response == null){
            throw new CustomException(ErrorType.TASK_NOT_FOUND);
        }
        return response;
    }

    public TaskOutline findDashboard(){
        return taskRepository.findDashboard();
    }
}