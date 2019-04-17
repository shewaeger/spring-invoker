package com.github.shewaeger.websocket.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.shewaeger.websocket.annotation.SendMethod;
import com.github.shewaeger.websocket.annotation.WebSocketController;
import com.github.shewaeger.websocket.dto.ControllerMethodInfoWrapper;
import com.github.shewaeger.websocket.dto.ControllerMethodInvokeWrapper;
import com.github.shewaeger.websocket.dto.ParameterWrapper;
import com.github.shewaeger.websocket.exception.IncorrectJsonObjectException;
import com.github.shewaeger.websocket.exception.UnableToInvokeMethodException;
import com.github.shewaeger.websocket.info.UpdatedControllerMethodInfo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSocketUpdatedHandlerService {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private ObjectMapper mapper;

    private Map<Long, UpdatedControllerMethodInfo> controllers = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Class<?>> beans = getAllClassesForAnnotation(WebSocketController.class);

        beans.forEach((name, bean) -> {
            Method[] methods = bean.getMethods();
            final Long[] i = {0L};
            for (Method method : methods) {
                if (!method.isAnnotationPresent(SendMethod.class))
                    continue;
                controllers.put(++i[0], new UpdatedControllerMethodInfo(method, bean, name));
            }
        });
    }

    public List<ControllerMethodInfoWrapper> getMethodInfo() {
        List<ControllerMethodInfoWrapper> returned = new ArrayList<>();
        controllers.forEach(
                (id, info) ->
                        returned.add(new ControllerMethodInfoWrapper(id, info, mapper))
        );
        return returned;
    }

    public Object invokeMethod(Long id, String rawArgs) {
        UpdatedControllerMethodInfo methodInfo = controllers.get(id);
        Method method = methodInfo.getMethod();
        String beanId = methodInfo.getBeanId();
        Object bean = beanFactory.getBean(beanId);

        Object[] args = getParametersFromRest(method, rawArgs);

        try {
            return method.invoke(bean, args);
        } catch (IllegalAccessException e) {
            throw new UnableToInvokeMethodException(e, "Method %s is non public", method.getName());
        } catch (InvocationTargetException e) {
            throw new UnableToInvokeMethodException(e, "Unable to invoke method %s", method.getName());
        }
    }

    private Object[] getParametersFromRest(Method method, String args) {
        List<Object> returned = new ArrayList<>();
        int parameterCount = method.getParameterCount();
        Class<?>[] parameterTypes = method.getParameterTypes();

        JavaType type = mapper.getTypeFactory().constructType(new TypeReference<List<ParameterWrapper>>() {});

        List<ParameterWrapper> arguments;
        try {
            arguments = mapper.readValue(args, type);
        } catch (IOException e) {
            throw new IncorrectJsonObjectException(e, "Object %s is incorrect", args);
        }

        for (ParameterWrapper argument : arguments) {
            //argument.
        }
        return null;
    }

    //Get information about beans without creating them
    private Map<String, Class<?>> getAllClassesForAnnotation(Class<? extends Annotation> annotation) {
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(annotation);
        Map<String, Class<?>> beanClasses = new HashMap<>();

        for (String beanName : beanNames) {

            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                beanClasses.put(beanName, clazz);
            } catch (ClassNotFoundException ignore) {
            }
        }

        return beanClasses;
    }

}
