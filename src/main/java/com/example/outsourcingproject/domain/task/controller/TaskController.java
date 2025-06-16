package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.common.dto.ApiResponse;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.repository.QTaskRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.task.service.TaskService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ApiResponse<?> getTaskList(
            @ModelAttribute TaskReadRequest request
            ){

        List<TaskResponse> taskList = taskService.findTasks(request);
        return ApiResponse.ok("조회 성공", taskList);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getTask(@PathVariable @NotNull Long id){
        TaskResponse task = taskService.findTask(id);
        return ApiResponse.ok("조회 성공", task);
    }
}
