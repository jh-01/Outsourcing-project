package com.example.outsourcingproject.domain.log.repository;

import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LogRepository extends JpaRepository<Log, Integer> {
    Page<Log> findByCreatedAtBetween(LocalDateTime createdAt, LocalDateTime createdAt2, Pageable pageable);

    Page<Log> findByTypeAndCreatedAtBetween(LogType type, LocalDateTime createdAt, LocalDateTime createdAt2, Pageable pageable);
}
