package com.example.outsourcingproject.domain.dashboard.service;

import com.example.outsourcingproject.domain.dashboard.dto.DashboardResponse;
import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import com.example.outsourcingproject.domain.user.service.UserService;
import com.example.outsourcingproject.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    @DisplayName("존재하지 않는 유저 ID로 대시보드 조회 시 UnAuthorizeException 발생")
    void getDashboard_존재하지_않는_유저_예외발생() {
        // given
        Long invalidUserId = 999L;
        when(userService.existsById(invalidUserId)).thenReturn(false);

        // when & then
        CustomException customException = assertThrows(
                CustomException.class,
                () -> dashboardService.getDashboard(invalidUserId)
        );

        verify(userService).existsById(invalidUserId);
        verifyNoMoreInteractions(userService, taskService);
    }

    @Test
    @DisplayName("유효한 유저 ID로 대시보드 조회 시 정상 작동")
    void getDashboard_정상작동() {
        // given
        Long validUserId = 1L;
        when(userService.existsById(validUserId)).thenReturn(true);

        // Mock 데이터를 더 구체적으로 설정
        TaskOutline mockOutline = createMockTaskOutline();
        List<TaskResponse> mockTaskList = createMockTaskList();

        when(taskService.findDashboard()).thenReturn(mockOutline);
        when(taskService.findTasks(any(TaskReadRequest.class))).thenReturn(mockTaskList);

        // when
        DashboardResponse response = dashboardService.getDashboard(validUserId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTaskOutline()).isEqualTo(mockOutline);
        assertThat(response.getTaskList()).isEqualTo(mockTaskList);
        assertThat(response.getTaskList()).hasSize(2);

        verify(userService).existsById(validUserId);
        verify(taskService).findDashboard();
        verify(taskService).findTasks(any(TaskReadRequest.class));

        // TaskReadRequest의 특정 필드 검증 (필요시)
        ArgumentCaptor<TaskReadRequest> requestCaptor = ArgumentCaptor.forClass(TaskReadRequest.class);
        verify(taskService).findTasks(requestCaptor.capture());
        TaskReadRequest capturedRequest = requestCaptor.getValue();
        // assertThat(capturedRequest.getUserId()).isEqualTo(validUserId);
    }

    @Test
    @DisplayName("taskService.findDashboard() 호출 시 예외 발생하는 경우")
    void getDashboard_findDashboard_예외발생() {
        // given
        Long validUserId = 1L;
        when(userService.existsById(validUserId)).thenReturn(true);
        when(taskService.findDashboard()).thenThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> dashboardService.getDashboard(validUserId));

        verify(userService).existsById(validUserId);
        verify(taskService).findDashboard();
        verifyNoMoreInteractions(taskService); // findTasks는 호출되지 않아야 함
    }

    @Test
    @DisplayName("taskService.findTasks() 호출 시 예외 발생하는 경우")
    void getDashboard_findTasks_예외발생() {
        // given
        Long validUserId = 1L;
        TaskOutline mockOutline = createMockTaskOutline();

        when(userService.existsById(validUserId)).thenReturn(true);
        when(taskService.findDashboard()).thenReturn(mockOutline);
        when(taskService.findTasks(any(TaskReadRequest.class)))
                .thenThrow(new RuntimeException("Task retrieval failed"));

        // when & then
        assertThrows(RuntimeException.class, () -> dashboardService.getDashboard(validUserId));

        verify(userService).existsById(validUserId);
        verify(taskService).findDashboard();
        verify(taskService).findTasks(any(TaskReadRequest.class));
    }

    @Test
    @DisplayName("빈 태스크 목록이 반환되는 경우")
    void getDashboard_빈_태스크목록() {
        // given
        Long validUserId = 1L;
        TaskOutline mockOutline = createMockTaskOutline();
        List<TaskResponse> emptyTaskList = Collections.emptyList();

        when(userService.existsById(validUserId)).thenReturn(true);
        when(taskService.findDashboard()).thenReturn(mockOutline);
        when(taskService.findTasks(any(TaskReadRequest.class))).thenReturn(emptyTaskList);

        // when
        DashboardResponse response = dashboardService.getDashboard(validUserId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTaskOutline()).isEqualTo(mockOutline);
        assertThat(response.getTaskList()).isEmpty();
    }

    // Helper methods for creating mock objects
    private TaskOutline createMockTaskOutline() {
        TaskOutline outline = new TaskOutline();
        // outline의 필드들을 적절히 설정
        // outline.setTotalTasks(10);
        // outline.setCompletedTasks(5);
        // outline.setPendingTasks(5);
        return outline;
    }

    private List<TaskResponse> createMockTaskList() {
        TaskResponse task1 = new TaskResponse();
        TaskResponse task2 = new TaskResponse();
        // task 객체들의 필드를 적절히 설정
        // task1.setId(1L);
        // task1.setTitle("Test Task 1");
        // task2.setId(2L);
        // task2.setTitle("Test Task 2");
        return List.of(task1, task2);
    }
}