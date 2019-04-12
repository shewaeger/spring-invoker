package org.communis.websocket.tester.util;

import org.springframework.messaging.handler.annotation.SendTo;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NameGenerator {

    private static final String startsWithString = "sendTo";

    public static List<String> generateChannelFromMethod(Method method){
        if(method.isAnnotationPresent(SendTo.class))
            return Arrays.asList(method.getAnnotation(SendTo.class).value());

        String methodName = method.getName();
        if(methodName.startsWith(startsWithString))
            methodName = methodName.substring(startsWithString.length());

        String[] queuePath = methodName.split("(?=\\p{Lu})"); //split by upper case characters

        for (int i = 0; i < queuePath.length; i++) {
            queuePath[i] = queuePath[i].toLowerCase();
        }

        return Collections.singletonList("/" + String.join("/", queuePath));
    }

}
