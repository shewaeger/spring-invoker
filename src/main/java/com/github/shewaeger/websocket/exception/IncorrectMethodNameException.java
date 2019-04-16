package com.github.shewaeger.websocket.exception;

public class IncorrectMethodNameException extends RuntimeException {
    public IncorrectMethodNameException(String msg, Object... args){
        super(String.format(msg, args));
    }
}
