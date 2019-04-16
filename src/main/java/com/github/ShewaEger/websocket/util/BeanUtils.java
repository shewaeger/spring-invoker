package com.github.ShewaEger.websocket.util;

import com.github.ShewaEger.websocket.exception.IncorrectMethodNameException;
import com.github.ShewaEger.websocket.exception.MethodHasChannelsDuplicatesException;
import org.springframework.messaging.handler.annotation.SendTo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtils {

    private static final String startsWithString = "sendTo";

    public static List<String> generateChannelFromMethod(Method method) {
        List<String> channels = new ArrayList<>();
        if (method.isAnnotationPresent(SendTo.class))
            channels.addAll(Arrays.asList(method.getAnnotation(SendTo.class).value()));

        String methodName = method.getName();
        if (methodName.startsWith(startsWithString)) {
            methodName = methodName.substring(startsWithString.length());
        } else if (channels.isEmpty()) {
            throw new IncorrectMethodNameException(
                    "In %s: Method must have @SendTo annotation or start with \"sendTo\".",
                    method.getName()
            );
        } else {
            return channels;
        }

        if (methodName.isEmpty()) {
            throw new IncorrectMethodNameException(
                    "In %s: Method does not contain channel in his name.",
                    method.getName()
            );
        }

        String[] queuePath = methodName.split("(?=\\p{Lu})"); //split by upper case characters

        for (int i = 0; i < queuePath.length; i++) {
            queuePath[i] = queuePath[i].toLowerCase();
        }

        channels.add("/" + String.join("/", queuePath));
        if (isDuplicatesHave(channels)) {
            throw new MethodHasChannelsDuplicatesException(
                    "Id %s: Method have duplicates of channels.",
                    method.getName()
            );
        }
        return channels;
    }

    private static boolean isDuplicatesHave(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).equals(s))
                    return true;
            }
        }
        return false;
    }

    public static boolean isClassPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz.equals(String.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Boolean.class) /*||
                clazz.isEnum()*/;
    }
}
