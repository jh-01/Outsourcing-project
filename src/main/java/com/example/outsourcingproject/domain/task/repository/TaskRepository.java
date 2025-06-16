package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    default Task findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 태스크 : " + id));
        // TODO : TaskNotFoundException	404 NOT_FOUND
    }
}
