package com.example.outsourcingproject.domain.dashboard.controller;

import com.example.outsourcingproject.domain.dashboard.service.DashboardService;
import com.example.outsourcingproject.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ApiResponse<?> getDashboard(
            HttpServletRequest request
    ) {
        Number id = (Number) request.getAttribute("id");
        return ApiResponse.createSuccess("대시보드 조회 성공", dashboardService.getDashboard(id.longValue()));
    }
}
