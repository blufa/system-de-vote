package com.senatic.votingserver.controller.exceptionHandler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityAlreadyExistException extends IllegalStateException {

    private String message;

    public EntityAlreadyExistException(String message){
        super(message);
        this.message = message;
    }
    
}
