package org.communis.websocket.tester.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Throwable parent){
        super(parent);
    }

    public NotFoundException(String message, Object args){
        super(String.format(message, args));
    }
}
