package com.senatic.votingserver.controller.exceptionHandler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityAlreadyOnStateException extends IllegalCallerException {

    private String message;

    public EntityAlreadyOnStateException(String message){
        super(message);
        this.message = message;
    }
}
