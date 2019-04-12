package org.communis.websocket.tester.exception;

public class UnableToInvokeMethodException extends RuntimeException {

    public UnableToInvokeMethodException(Throwable parent, String message, Object args){
        super(String.format(message, args), parent);
    }

}
