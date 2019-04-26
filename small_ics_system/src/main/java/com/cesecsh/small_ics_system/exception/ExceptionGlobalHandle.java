package com.cesecsh.small_ics_system.exception;

import com.cesecsh.small_ics_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionGlobalHandle {
    @ExceptionHandler
    public Result handle(Exception e) {
        log.error("", e);
        return Result.error(e.getMessage());
    }
}
