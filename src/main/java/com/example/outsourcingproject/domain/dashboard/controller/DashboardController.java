package com.example.outsourcingproject.domain.dashboard.controller;

import com.example.outsourcingproject.domain.dashboard.service.DashboardService;
import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.user.service.UserService;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResponse<?> getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        return ApiResponse.ok("대시보드 조회 성공", dashboardService.getDashboard(userId));
    }
}
