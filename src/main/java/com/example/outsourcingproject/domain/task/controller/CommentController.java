package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.task.dto.CommentRequestDto;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    // 댓글 생성
    @PostMapping("/{task_id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("task_id") Long taskId,
                                                            @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto =  commentService.addComment(taskId, requestDto.getContents());

        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);

    }

    // 댓글 수정
    @PatchMapping("/{task_id}/comments/{id}")
    public ResponseEntity<CommentResponseDto> changeComment(@PathVariable("task_id") Long taskId,
                                                            @PathVariable("id") Long commentId,
                                                            @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto = commentService.updateComment(taskId, commentId, requestDto.getContents());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
