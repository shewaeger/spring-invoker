package org.communis.websocket.tester.exception;

import org.springframework.beans.BeansException;

public class MethodHasInvalidParameters extends BeansException {

    public MethodHasInvalidParameters(String msg) {
        super(msg);
    }

    public MethodHasInvalidParameters(String msg, Object ... args){
        super(String.format(msg, args));
    }

    public MethodHasInvalidParameters(String msg, Throwable cause) {
        super(msg, cause);
    }
}
