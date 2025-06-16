package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.task.dto.CommentRequestDto;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.service.CommentService;
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
    @PostMapping("/{task_id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("task_id") Long taskId,
                                                            @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto =  commentService.addComment(taskId, requestDto.getContents());

        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);

    }

    // 댓글 수정
    @PatchMapping("/{task_id}/comments/{id}")
    public ResponseEntity<CommentResponseDto> changeComment(@PathVariable("task_id") Long taskId,
                                                            @PathVariable("id") Long commentId,
                                                            @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto = commentService.updateComment(taskId, commentId, requestDto.getContents());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    // 댓글 조회
    @GetMapping("/{task_id}/comments")
    public ResponseEntity<Page<CommentResponseDto>> getComments(@PathVariable("task_id") Long taskId,
                                                                // 페이지 기본값 설정
                                                                @PageableDefault(page = 0, size = 10, sort = "cratedAt", direction = Sort.Direction.DESC)
                                                                Pageable pageable,
                                                                @RequestParam(required = false) String keyword) {

        Page<CommentResponseDto> commentList = commentService.getCommentList(taskId, pageable, keyword);

        return new ResponseEntity<>(commentList, HttpStatus.OK);

    }

    // 댓글 삭제
    @DeleteMapping("/{task_id}/comments/{id}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable("task_id") Long taskId,
                                                            @PathVariable("id") Long commentId) {

        CommentResponseDto responseDto = commentService.softDeleteComment(taskId, commentId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
