package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.task.dto.CommentRequestDto;
import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.dto.response.CommentData;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.service.CommentService;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @LogWrite(type = LogType.COMMENT_CREATED)
    @PostMapping("/{task_id}/comments")
    public ResponseEntity<ApiResponse<CommentData>> createComment(@PathVariable("task_id") Long taskId,
                                                                  @Valid @RequestBody CommentRequestDto requestDto,
                                                                  HttpServletRequest servletRequest) {

        ApiResponse<CommentData> responseDto =  commentService.addComment(taskId, requestDto.getContents(), servletRequest);

        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);

    }

    // 댓글 수정
    @LogWrite(type = LogType.COMMENT_UPDATED)
    @PatchMapping("/{task_id}/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseData>> changeComment(@PathVariable("task_id") Long taskId,
                                                            @PathVariable("id") Long commentId,
                                                            @Valid @RequestBody CommentRequestDto requestDto) {

        ApiResponse<CommentResponseData> responseDto = commentService.updateComment(taskId, commentId, requestDto.getContents());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    // 댓글 조회
    @GetMapping("/{task_id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseData>>> getComments(@PathVariable("task_id") Long taskId,
                                                                // 페이지 기본값 설정
                                                                @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
                                                                Pageable pageable,
                                                                @RequestParam(required = false) String keyword) {

        ApiResponse<List<CommentResponseData>> response = commentService.getCommentList(taskId, pageable, keyword);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 댓글 삭제
    @LogWrite(type = LogType.COMMENT_DELETED)
    @DeleteMapping("/{task_id}/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseData>> deleteComment(@PathVariable("task_id") Long taskId,
                                                            @PathVariable("id") Long commentId) {

        ApiResponse<CommentResponseData> responseDto = commentService.softDeleteComment(taskId, commentId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
