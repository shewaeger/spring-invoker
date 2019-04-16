package com.github.shewaeger.websocket.info;

import com.github.shewaeger.websocket.util.BeanUtils;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class ControllerMethodInfo {

    private Method method;

    private Class<?> owner;

    private String beanId;

    private Class<?> parameter;

    private Boolean isParameterPrimitive;

    private Boolean hasUser;

    private List<String> channels;

    public ControllerMethodInfo(Method method, Class owner, String beanId) {
        this.owner = owner;
        this.method = method;
        this.beanId = beanId;
        this.hasUser = method.getParameterCount() > 1;
        this.channels = BeanUtils.generateChannelFromMethod(method);
        this.parameter = method.getParameterCount() > 1 ? method.getParameterTypes()[1] : method.getParameterTypes()[0];
        this.isParameterPrimitive =  BeanUtils.isClassPrimitive(this.parameter);
    }


}
