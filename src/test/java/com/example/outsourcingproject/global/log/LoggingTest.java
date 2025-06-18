package com.example.outsourcingproject.global.log;

import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.log.repository.LogRepository;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoggingTest {

    @Mock
    HttpServletRequest request;

    @Mock
    LogRepository logRepository;

    @Mock
    ProceedingJoinPoint joinPoint;

    @Mock
    LogWrite write;

    @InjectMocks
    Logging logging;

    @Test
    @DisplayName(" Response_객체로_TargetId_찾기_성공 ")
    void save() throws Throwable {
        //given
        LoggableResponse response = () -> 3;
        given(joinPoint.proceed()).willReturn(response);
        given(request.getMethod()).willReturn("POST");
        given(request.getRequestURI()).willReturn("/api/tasks");
        given(request.getRemoteAddr()).willReturn("127.0.0.1");

        given(write.type()).willReturn(LogType.TASK_CREATED);

        // when
        Object result = logging.save(joinPoint, write);

        // then
        assertEquals(3, ((LoggableResponse) result).getTargetId());
    }
}