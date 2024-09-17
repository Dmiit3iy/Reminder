package com.dmiit3iy.reminder.controller;

import com.dmiit3iy.reminder.DTO.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseResult<Exception>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ResponseResult<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

}
