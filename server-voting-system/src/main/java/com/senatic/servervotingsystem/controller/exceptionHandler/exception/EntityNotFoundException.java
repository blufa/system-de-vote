package com.senatic.servervotingsystem.controller.exceptionHandler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityNotFoundException extends IllegalStateException {

    private String message;

    public EntityNotFoundException(String message){
        super(message);
        this.message = message;
    }
    
}
