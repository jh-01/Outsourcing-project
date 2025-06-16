package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndTaskId(Long taskId, Long commentId);
}
