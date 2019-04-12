package org.communis.websocket.tester.info;

import lombok.Data;
import org.communis.websocket.tester.util.NameGenerator;

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

    public ControllerMethodInfo(Method method, Class owner, String beanId){
        this.owner = owner;
        this.method = method;
        this.beanId = beanId;
        this.hasUser = method.getParameterCount() > 1;
        this.channels = NameGenerator.generateChannelFromMethod(method);
        this.parameter = method.getParameterCount() > 1 ? method.getParameterTypes()[1] : method.getParameterTypes()[0];
        this.isParameterPrimitive = isClassPrimitive(this.parameter);
    }


    private boolean isClassPrimitive(Class<?> clazz){

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
