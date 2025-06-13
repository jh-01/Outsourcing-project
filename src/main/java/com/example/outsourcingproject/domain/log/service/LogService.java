package com.example.outsourcingproject.domain.log.service;

import com.example.outsourcingproject.domain.log.dto.LogResponse;
import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public Page<LogResponse> getLogs(LogType type, Pageable pageable, LocalDate startDate, LocalDate endDate) {
        Page<Log> logPage;
        if ( type == LogType.ALL ){
            logPage = logRepository.findByCreatedAtBetween(startDate,endDate,pageable);
        }
        else{
            logPage = logRepository.findByTypeAndCreatedAtBetween(type,startDate,endDate,pageable);
        }

        return logPage.map(LogResponse::toDto);
    }

}
