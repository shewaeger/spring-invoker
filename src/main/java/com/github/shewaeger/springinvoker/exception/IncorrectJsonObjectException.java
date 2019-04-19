package com.github.shewaeger.springinvoker.exception;

public class IncorrectJsonObjectException extends RuntimeException {

    public IncorrectJsonObjectException(Throwable parent, String message, Object... args) {
        super(String.format(message, args), parent);
    }

    public IncorrectJsonObjectException(String message, Object...args){
        super(String.format(message, args));
    }
}
