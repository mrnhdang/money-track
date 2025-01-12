package com.huda.money_track.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDate.now());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidInputParameter.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidParameterException(RuntimeException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, LocalDate.now());
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
