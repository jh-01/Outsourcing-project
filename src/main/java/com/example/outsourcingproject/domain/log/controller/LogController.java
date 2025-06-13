package com.example.outsourcingproject.domain.log.controller;

import com.example.outsourcingproject.domain.log.dto.LogResponse;
import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("/logs")
    public ResponseEntity<Page<LogResponse>> getLog(@RequestParam(defaultValue = "ALL") LogType type,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (endDate == null) {
            endDate = now;
        }
        if (startDate == null) {
            startDate = endDate.minusDays(7);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(logService.getLogs(type, PageRequest.of(page,size), startDate, endDate));
    }

    @GetMapping("/logs/{logId}")
    public ResponseEntity<LogResponse> getLog(@PathVariable int logId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(logService.getLog(logId));
    }
}
