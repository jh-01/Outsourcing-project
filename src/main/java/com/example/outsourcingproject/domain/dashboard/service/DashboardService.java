package com.example.outsourcingproject.domain.dashboard.service;

import com.example.outsourcingproject.domain.dashboard.dto.DashboardResponse;
import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import com.example.outsourcingproject.domain.user.service.UserService;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserService userService;
    private final TaskService taskService;

    public DashboardResponse getDashboard(Long userId) {
        // 존재하는 유저 아이디인지 확인
        if(!userService.existsById(userId)) throw new CustomException(ErrorType.USER_NOT_FOUND);

        TaskOutline taskOutline = taskService.findDashboard();
        TaskReadRequest taskReadRequest = new TaskReadRequest(userId);
        List<TaskResponse> userTasks = taskService.findTasks(taskReadRequest);
        return new DashboardResponse(taskOutline, userTasks);
    }
}
