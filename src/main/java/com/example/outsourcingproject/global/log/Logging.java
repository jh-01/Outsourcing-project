package com.example.outsourcingproject.global.log;

import com.example.outsourcingproject.domain.log.entity.IdSource;
import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.log.repository.LogRepository;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import com.example.outsourcingproject.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class Logging {
    private final HttpServletRequest request;
    private final LogRepository logRepository;
    private final JwtUtil jwtUtil;

    @Around("@annotation(logWrite)")
    @Transactional
    public Object save(ProceedingJoinPoint joinPoint, LogWrite logWrite) throws Throwable {
        Object result = joinPoint.proceed();
        LogType type = logWrite.type();

        int userId = extractId(type.getUserSource(),type.getTargetKey(), result, joinPoint);
        int targetId = extractId(type.getTargetSource(),type.getTargetKey(), result, joinPoint);

        String requestMethod = request.getMethod();
        String requestURL = request.getRequestURI();
        String address = request.getRemoteAddr();

        Log log = new Log(targetId,userId,type,requestMethod,requestURL,address);

        logRepository.save(log);
        return result;
    }

    private int extractId(IdSource source,String targetKey, Object result, ProceedingJoinPoint joinPoint) {
        return switch (source) {
            case TOKEN -> extractFromToken(result);
            case PATH_VARIABLE -> extractFromPathVariable(joinPoint, targetKey);
            case RESPONSE -> extractFromResponse(result);
            case NULL -> -1;
        };
    }

    private int extractFromPathVariable(ProceedingJoinPoint joinPoint,String targetKey) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (param.isAnnotationPresent(PathVariable.class)) {
                PathVariable pv = param.getAnnotation(PathVariable.class);

                String name = pv.value().isEmpty() ? param.getName() : pv.value();

                if (name.equals(targetKey)) {
                    Object value = args[i];
                    if (value instanceof Integer) {
                        return (Integer) value;
                    }
                    if (value instanceof Long) {
                        return ((Long) value).intValue();
                    }
                }
            }
        }

        return -1;
    }

    private int extractFromToken(Object result) {
        if (request.getAttribute("id") == null) {
            if (result instanceof ApiResponse<?> responseEntity) {
                Map<String, String> body = (Map<String, String>) responseEntity.getData();
                String token = body.get("token");
                return jwtUtil.extractId(token);
            }
            return -1;
        } else {
            return (int) request.getAttribute("id");
        }
    }

    private int extractFromResponse(Object result) {
        if (result instanceof ApiResponse<?> responseEntity) {
            Object body = responseEntity.getData();
            if (body instanceof LoggableResponse loggable) {
                return loggable.getTargetId();
            }
        }
        return -1;
    }

}