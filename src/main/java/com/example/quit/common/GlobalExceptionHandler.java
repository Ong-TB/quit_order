package com.example.quit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        if (e.getMessage().contains("Duplicate entry")) {
            String[] s = e.getMessage().split(" ");
            String msg = s[2] + " already exists.";
            return Result.error(msg);
        }
        return Result.error("Unknown error.");
    }

    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException e) {
        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }
}
