package com.cineflow.exception;

import lombok.Getter;

@Getter
public class CineflowException extends RuntimeException{

    private final String messageKey;

    public CineflowException (String messageKey){
        super(messageKey);
        this.messageKey = messageKey;
    }

    public String getMessageKey(){
        return messageKey;
    }
}
