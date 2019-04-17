package com.github.shewaeger.websocket.info;

import com.github.shewaeger.websocket.util.BeanUtils;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UpdatedControllerMethodInfo {

    private Method method;

    private Class<?> owner;

    private String beanId;

    private List<Class<?>> parameters;


    public UpdatedControllerMethodInfo(Method method, Class owner, String beanId) {
        this.owner = owner;
        this.method = method;
        this.beanId = beanId;
        parameters = Arrays.asList(method.getParameterTypes());
    }


}
