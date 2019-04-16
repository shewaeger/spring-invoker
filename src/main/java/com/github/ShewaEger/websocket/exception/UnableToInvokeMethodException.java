package com.github.ShewaEger.websocket.exception;

public class UnableToInvokeMethodException extends RuntimeException {

    public UnableToInvokeMethodException(Throwable parent, String message, Object args) {
        super(String.format(message, args), parent);
    }

}
