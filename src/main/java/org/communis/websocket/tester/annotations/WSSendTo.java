package org.communis.websocket.tester.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WSSendTo {
    String value();
}
