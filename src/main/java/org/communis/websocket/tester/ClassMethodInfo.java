package org.communis.websocket.tester;

import lombok.Data;
import org.communis.websocket.tester.utils.NameGenerator;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class ClassMethodInfo {

    private Method method;

    private Class<?> owner;

    private String beanId;

    private Class<?> parameter;

    private String user;

    private List<String> channels;

    public ClassMethodInfo(Method method, Class owner, String beanId){
        this.owner = owner;
        this.method = method;
        this.beanId = beanId;

        channels = NameGenerator.generateChannelFromMethod(method);

        this.parameter = method.getParameterTypes()[0];
    }

}
