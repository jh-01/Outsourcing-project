package com.example.outsourcingproject.domain.task.comment.controller;

import com.example.outsourcingproject.domain.task.controller.CommentController;
import com.example.outsourcingproject.domain.task.dto.CommentRequestDto;
import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.service.CommentService;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockitoBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    // main 의 application 에 있는 @EnableJpaAuditing 주석 처리 안하면 제대로 안돌아감.
    @Test
    public void createCommentTest() throws Exception{

        // given - 요청 dto, 응답 dto, PathVariable, data 객체 , service 행위
        CommentRequestDto request = new CommentRequestDto("댓글 내용");

        Long taskId = 1L;

        CommentResponseData reponseData = new CommentResponseData(taskId, "테스트용 유저명", request.getContents());

        ApiResponse<CommentResponseData> response = ApiResponse.createSuccess("댓글 생성 성공!", reponseData);

        when(commentService.addComment(1L, request.getContents()))
                .thenReturn(response);

        // when - Controller 의 로그 찍어놓은것 지워야함.
        ResultActions result = mockMvc
                .perform(post("/api/tasks/" + taskId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then - HttpStatus 검증으로 확인
        result.andExpect(status().isCreated());

    }

    @Test
    public void changeCommentTest() throws Exception {

        // given - PathVariable, 요청 dto, 응답 dto, Service 행위
        Long taskId = 1L;
        Long commentId = 1L;

        CommentRequestDto request = new CommentRequestDto("수정된 내용");

        ApiResponse<CommentResponseData> response = ApiResponse.createSuccess("수정된 내용", null);

        when(commentService.updateComment(taskId, commentId, request.getContents()))
                .thenReturn(response);

        // when
        ResultActions result = mockMvc.perform(patch("/api/tasks/" + taskId + "/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then - HttpStatus 검증으로 확인
        result.andExpect(status().isOk());

    }


}
