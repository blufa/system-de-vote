package com.senatic.servervotingsystem.controller.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class ApiExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void constraintViolationHandler(ConstraintViolationException exception){
        log.error(exception.getLocalizedMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void bindExceptionHandler(BindException exception){
        log.error(exception.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void principalExceptionHandler(Exception exception){
        log.error(exception.getLocalizedMessage());

    }
    
}
