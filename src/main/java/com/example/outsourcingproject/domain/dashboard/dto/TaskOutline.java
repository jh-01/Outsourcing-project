package com.example.outsourcingproject.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskOutline {
    private Long totalCount;
    private Long todoCount;
    private Long inProgressCount;
    private Long doneCount;
    private double completionRate;
    private Long overdueCount;
}
