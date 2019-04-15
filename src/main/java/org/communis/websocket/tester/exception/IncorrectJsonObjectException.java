package org.communis.websocket.tester.exception;

public class IncorrectJsonObjectException extends RuntimeException {

    public IncorrectJsonObjectException(Throwable parent){
        super(parent);
    }

    public IncorrectJsonObjectException(Throwable parent, String message, Object ... args){
        super(String.format(message, args), parent);
    }

}
