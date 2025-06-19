package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//public class TaskServiceIntegrationTest {
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    private Long generatorId;
//    private Long managerId;
//
//
////    @BeforeEach
////    void setUp() {
////        User generator = userRepository.save(User.builder()
////                .username("생성자")
////                .build());
////
////        User manager = userRepository.save(User.builder()
////                .name("담당자")
////                .email("mgr@example.com")
////                .password("password")
////                .build());
////
////        generatorId = generator.getId();
////        managerId = manager.getId();
////    }
//
//
////    @Test
////    @DisplayName("태스크 단건 조회")
////    void 태스크_단건_성공(){
////        // given
////        // 통합 후에 실제 생성 후 조회하기
////        TaskResponse mockTask = new TaskResponse(
////                1L,
////                "John Doe",
////                "John Doe",
////                "d",
////                "d",
////                Priority.LOW,
////                LocalDateTime.parse("2025-06-17T17:21:54.000000"),
////                Status.TODO,
////                LocalDateTime.parse("2025-06-17T15:56:03.000000"),
////                LocalDateTime.parse("2025-06-17T15:55:44.000000"),
////                LocalDateTime.parse("2025-06-17T15:55:48.000000")
////        );
////
////
////        //when
////        TaskResponse targetTask = taskService.findTask(1L);
////
////
////        // then
////        assertThat(targetTask).isNotNull();
////        assertThat(targetTask).isEqualTo(mockTask);
////    }
//
//}
