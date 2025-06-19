package com.example.outsourcingproject.domain.task.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskListResponse;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, null); // UserRepository가 필요 없을 경우 null
    }

    @Test
    void task_find_success(){
        // given
        TaskResponse mockTask = new TaskResponse();

        // when
        when(taskRepository.findTaskById(1L)).thenReturn(mockTask);
        TaskResponse targetTask = taskService.findTask(1L);

        // then
        assertThat(targetTask).isNotNull();
        assertThat(targetTask).isEqualTo(mockTask);
    }

    @Test
    void test_list_find_success(){
        // Given
        TaskReadRequest taskReadRequest = new TaskReadRequest(0, 10, "제목", "내용", Status.TODO, 1L);
        List<TaskResponse> mockTaskResponseList = Collections.singletonList(new TaskResponse());
        Page<TaskResponse> mockPage = new PageImpl<>(mockTaskResponseList, PageRequest.of(0, 10), 1L);

        // when
        TaskListResponse mockTaskListResponse = new TaskListResponse();
        when(taskRepository.findTasks(taskReadRequest)).thenReturn(mockPage);
        TaskListResponse targetTaskListResponse = taskService.findTasks(taskReadRequest);

        // then
        assertThat(mockTaskListResponse).isNotNull();
        assertThat(targetTaskListResponse).isNotNull();
        assertThat(targetTaskListResponse.getContent()).isEqualTo(mockTaskResponseList);
    }

    @Test
    void find_task_list_failed(){

    }
}


