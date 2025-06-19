//package com.example.outsourcingproject.domain.task.service;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
//import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
//import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
//import com.example.outsourcingproject.domain.task.entity.Task;
//import com.example.outsourcingproject.domain.task.repository.TaskRepository;
//import com.example.outsourcingproject.global.exception.CustomException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Collections;
//
//@ExtendWith(MockitoExtension.class)
//class TaskServiceTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @InjectMocks
//    private TaskService taskService;
//
////    @Test
////    @DisplayName("태스크 목록 조회 - 정상 작동")
////    void findTasks_정상작동() {
////        // given
////        TaskReadRequest request = createTaskReadRequest();
////        List<TaskResponse> expectedTasks = createMockTaskList();
////
////        when(taskRepository.findTasks(request)).thenReturn(expectedTasks);
////
////        // when
////        List<TaskResponse> result = taskService.findTasks(request);
////
////        // then
////        assertThat(result).isNotNull();
////        assertThat(result).hasSize(2);
////        assertThat(result).isEqualTo(expectedTasks);
////
////        verify(taskRepository).findTasks(request);
////    }
//
////    @Test
////    @DisplayName("태스크 목록 조회 - 빈 목록 반환")
////    void findTasks_빈목록반환() {
////        // given
////        TaskReadRequest request = createTaskReadRequest();
////        List<TaskResponse> emptyList = Collections.emptyList();
////
////        when(taskRepository.findTasks(request)).thenReturn(emptyList);
////
////        // when
////        List<TaskResponse> result = taskService.findTasks(request);
////
////        // then
////        assertThat(result).isNotNull();
////        assertThat(result).isEmpty();
////
////        verify(taskRepository).findTasks(request);
////    }
//
//    @Test
//    @DisplayName("태스크 목록 조회 - Repository에서 예외 발생")
//    void findTasks_Repository예외발생() {
//        // given
//        TaskReadRequest request = createTaskReadRequest();
//
//        when(taskRepository.findTasks(request)).thenThrow(new RuntimeException("Database error"));
//
//        // when & then
//        assertThrows(RuntimeException.class, () -> taskService.findTasks(request));
//
//        verify(taskRepository).findTasks(request);
//    }
//
//    @Test
//    @DisplayName("단일 태스크 조회 - 정상 작동")
//    void findTask_정상작동() {
//        // given
//        Long taskId = 1L;
//        TaskResponse mockTask = createMockTaskResponse(taskId); // Task 엔티티 생성
//
//        when(taskRepository.findTaskById(taskId)).thenReturn(mockTask); // Task 반환
//
//        // when
//        TaskResponse result = taskService.findTask(taskId);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(taskId);
//
//        verify(taskRepository).findTaskById(taskId);
//    }
//
//    // Task 엔티티를 생성하는 헬퍼 메서드
//    private TaskResponse createMockTaskResponse(Long taskId) {
//        return TaskResponse.builder()
//                .id(taskId)
//                .title("테스트 태스크")
//                .description("테스트 설명")
//                .build();
//    }
//
//    @Test
//    @DisplayName("단일 태스크 조회 - 존재하지 않는 태스크로 예외 발생")
//    void findTask_존재하지않는태스크_예외발생() {
//        // given
//        Long nonExistentTaskId = 999L;
//
//        when(taskRepository.findTaskById(nonExistentTaskId)).thenReturn(null);
//
//        // when & then
//        CustomException exception = assertThrows(
//                CustomException.class,
//                () -> taskService.findTask(nonExistentTaskId)
//        );
//
//        assertThat(exception.getMessage()).contains("존재하지 않는 태스크입니다.");
//
//        verify(taskRepository).findTaskById(nonExistentTaskId);
//    }
//
//    @Test
//    @DisplayName("단일 태스크 조회 - Repository에서 예외 발생")
//    void findTask_Repository예외발생() {
//        // given
//        Long taskId = 1L;
//
//        when(taskRepository.findTaskById(taskId)).thenThrow(new RuntimeException("Database connection failed"));
//
//        // when & then
//        assertThrows(RuntimeException.class, () -> taskService.findTask(taskId));
//
//        verify(taskRepository).findTaskById(taskId);
//    }
//
//    @Test
//    @DisplayName("대시보드 정보 조회 - 정상 작동")
//    void findDashboard_정상작동() {
//        // given
//        TaskOutline expectedOutline = createMockTaskOutline();
//
//        when(taskRepository.findDashboard()).thenReturn(expectedOutline);
//
//        // when
//        TaskOutline result = taskService.findDashboard();
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo(expectedOutline);
//        assertThat(result.getTotalCount()).isEqualTo(10L);
//        assertThat(result.getTodoCount()).isEqualTo(3L);
//        assertThat(result.getInProgressCount()).isEqualTo(4L);
//        assertThat(result.getDoneCount()).isEqualTo(3L);
//        assertThat(result.getCompletionRate()).isEqualTo(0.3);
//        assertThat(result.getOverdueCount()).isEqualTo(2L);
//
//        verify(taskRepository).findDashboard();
//    }
//
//    @Test
//    @DisplayName("대시보드 정보 조회 - Repository에서 예외 발생")
//    void findDashboard_Repository예외발생() {
//        // given
//        when(taskRepository.findDashboard()).thenThrow(new RuntimeException("Dashboard query failed"));
//
//        // when & then
//        assertThrows(RuntimeException.class, () -> taskService.findDashboard());
//
//        verify(taskRepository).findDashboard();
//    }
//
//    @Test
//    @DisplayName("대시보드 정보 조회 - null 반환 처리")
//    void findDashboard_null반환() {
//        // given
//        when(taskRepository.findDashboard()).thenReturn(null);
//
//        // when
//        TaskOutline result = taskService.findDashboard();
//
//        // then
//        assertThat(result).isNull();
//
//        verify(taskRepository).findDashboard();
//    }
//
//    // Helper methods for creating test data
//    private TaskReadRequest createTaskReadRequest() {
//        TaskReadRequest request = new TaskReadRequest(1L);
//        // request.setPage(0);
//        // request.setSize(10);
//        // request.setTitle("Test");
//        // request.setDescription("Description");
//        // request.setStatus(Status.TODO);
//        // request.setManagerId(1L);
//        return request;
//    }
//
//    private List<TaskResponse> createMockTaskList() {
//        TaskResponse task1 = createMockTask(1L);
//        TaskResponse task2 = createMockTask(2L);
//        return List.of(task1, task2);
//    }
//
//    private TaskResponse createMockTask(Long id) {
//        TaskResponse task = new TaskResponse();
//        // 실제 TaskResponse 생성자나 setter에 맞게 수정
//        // task.setId(id);
//        // task.setManagerName("Manager " + id);
//        // task.setGeneratorName("Generator " + id);
//        // task.setTitle("Task " + id);
//        // task.setDescription("Description " + id);
//        // task.setPriority(Priority.HIGH);
//        // task.setDeadline(LocalDateTime.now().plusDays(7));
//        // task.setStatus(Status.TODO);
//        // task.setStartAt(LocalDateTime.now());
//        // task.setCreatedAt(LocalDateTime.now());
//        // task.setModifiedAt(LocalDateTime.now());
//        return task;
//    }
//
//    private TaskOutline createMockTaskOutline() {
//        return new TaskOutline(
//                10L,    // totalCount
//                3L,     // todoCount
//                4L,     // inProgressCount
//                3L,     // doneCount
//                0.3,    // completionRate (3/10)
//                2L      // overDueDateCount
//        );
//    }
//}
//
//
