package com.github.shewaeger.springinvoker.exception;

public class IncorrectMethodNameException extends RuntimeException {
    public IncorrectMethodNameException(String msg, Object... args){
        super(String.format(msg, args));
    }
}
