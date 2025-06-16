package com.example.outsourcingproject.global.log.annotation;

import com.example.outsourcingproject.domain.log.entity.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogWrite {
    LogType type();
}
