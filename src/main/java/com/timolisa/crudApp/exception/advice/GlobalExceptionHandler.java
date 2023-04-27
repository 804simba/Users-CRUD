package com.timolisa.crudApp.exception.advice;

import com.timolisa.crudApp.dto.ResponseDto;
import com.timolisa.crudApp.exception.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        ResponseDto response = new ResponseDto();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> runtimeException(BadCredentialsException e) {
        ResponseDto response = new ResponseDto();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> runtimeException(NoSuchElementException e) {
        ResponseDto response = new ResponseDto();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
