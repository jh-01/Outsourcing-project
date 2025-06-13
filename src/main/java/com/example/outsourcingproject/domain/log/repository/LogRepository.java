package com.example.outsourcingproject.domain.log.repository;

import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LogRepository extends JpaRepository<Log, Integer> {
    Page<Log> findByCreatedAtBetween(LocalDate createdAtAfter, LocalDate createdAtBefore, Pageable pageable);

    Page<Log> findByTypeAndCreatedAtBetween(LogType type, LocalDate createdAtAfter, LocalDate createdAtBefore, Pageable pageable);
}
