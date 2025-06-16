package com.example.outsourcingproject.domain.dashboard.controller;

import com.example.outsourcingproject.domain.common.dto.ApiResponse;
import com.example.outsourcingproject.domain.dashboard.service.DashboardService;
import com.example.outsourcingproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final UserService userService;

    @GetMapping
    public ApiResponse<?> getDashboard(
            // @AuthenticationPrincipal Long userId
    ){
        return ApiResponse.ok("대시보드 조회 성공", dashboardService.getDashboard(1L));
    }
}
