package org.communis.websocket.tester.exceptions;

public class IncorrectJsonObject extends RuntimeException {

    public IncorrectJsonObject(Throwable parent){
        super(parent);
    }

    public IncorrectJsonObject(Throwable parent, String message, Object ... args){
        super(String.format(message, args), parent);
    }

}
