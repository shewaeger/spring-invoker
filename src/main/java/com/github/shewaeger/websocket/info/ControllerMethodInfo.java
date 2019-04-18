package com.github.shewaeger.websocket.info;

import com.github.shewaeger.websocket.annotation.SendMethod;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class ControllerMethodInfo {

    private Method method;

    private Class<?> owner;

    private String beanId;


    private String description;


    public ControllerMethodInfo(Method method, Class owner, String beanId) {
        this.owner = owner;
        this.method = method;
        this.beanId = beanId;
        SendMethod annotation = method.getAnnotation(SendMethod.class);
        this.description = annotation.description();
    }


}
