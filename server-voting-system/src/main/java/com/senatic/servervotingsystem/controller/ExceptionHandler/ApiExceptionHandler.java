package com.senatic.servervotingsystem.controller.ExceptionHandler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void constraintViolationHandler(ConstraintViolationException exception){
        logger.info(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void bindExceptionHandler(BindException exception){
        logger.info(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void principalExceptionHandler(Exception exception){
        logger.info(exception.getMessage());

    }
    
}
