package com.github.shewaeger.springinvoker.exception;

public class MethodHasChannelsDuplicatesException extends RuntimeException {
    public MethodHasChannelsDuplicatesException(String msg, Object... args) {
        super(String.format(msg, args));
    }
}
