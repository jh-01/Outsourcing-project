package com.example.outsourcingproject.domain.task.entity;

import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task")
@Setter
@Getter
public class Task extends BaseTimeEntity {
    @Id
    private Long id;

    private String manager;

    private String generator;

    private String title;

    private String description;

    private Priority priority;

    private LocalDateTime deadline;

    private Status status;

    private LocalDateTime startAt;

    private boolean isDeleted;

    private LocalDateTime deletedAt;
}
