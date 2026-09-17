package com.practice.OneYear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, WebRequest request){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("message",ex.getBindingResult()
                .getFieldErrors()
                .get(0)   // 👈 first error only
                .getDefaultMessage());
        map.put("status",HttpStatus.BAD_REQUEST);

        return new  ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("timestamp",LocalDateTime.now());
        map.put("message",ex.getMessage());
        map.put("status",HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }
}
