package com.senatic.votingserver.controller.exceptionHandler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senatic.votingserver.controller.exceptionHandler.exception.EntityAlreadyExistException;
import com.senatic.votingserver.controller.exceptionHandler.exception.EntityAlreadyOnStateException;
import com.senatic.votingserver.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.votingserver.controller.exceptionHandler.exception.FileNotValidException;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    public static final Logger logger = LogManager.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void constraintViolationHandler(ConstraintViolationException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void bindExceptionHandler(BindException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public void entityNotFoundExceptionHandler(EntityNotFoundException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT)
    public void entityAlreadyExistExceptionHandler(EntityAlreadyExistException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(FileNotValidException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void fileNotValidExceptionHandler(FileNotValidException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyOnStateException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT)
    public void entityAlreadyOnStateExceptionHandler(EntityAlreadyOnStateException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT)
    public void entityAlreadyOnStateExceptionHandler(IllegalStateException exception){
        logger.error(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void principalExceptionHandler(Exception exception){
        logger.error(exception.getMessage());
    }
    
}
