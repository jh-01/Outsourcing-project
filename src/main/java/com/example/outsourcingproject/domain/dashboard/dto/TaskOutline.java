package com.example.outsourcingproject.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskOutline {
    private int totalCount;
    private int todoCount;
    private int inProgressCount;
    private int doneCount;
    private double completionRate;
    private int overdueCount;
}
