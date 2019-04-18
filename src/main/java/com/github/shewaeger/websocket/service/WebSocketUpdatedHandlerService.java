package com.github.shewaeger.websocket.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shewaeger.websocket.annotation.InvokedClass;
import com.github.shewaeger.websocket.annotation.SendMethod;
import com.github.shewaeger.websocket.dto.ControllerMethodInfoWrapper;
import com.github.shewaeger.websocket.exception.IncorrectJsonObjectException;
import com.github.shewaeger.websocket.exception.UnableToInvokeMethodException;
import com.github.shewaeger.websocket.info.ControllerMethodInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

    private Map<Long, ControllerMethodInfo> controllers = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Class<?>> beans = getAllClassesForAnnotation(InvokedClass.class);
        final Long[] i = {0L};
        beans.forEach((name, bean) -> {
            Method[] methods = bean.getMethods();

            for (Method method : methods) {
                if (!method.isAnnotationPresent(SendMethod.class))
                    continue;
                controllers.put(++i[0], new ControllerMethodInfo(method, bean, name));
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
        ControllerMethodInfo methodInfo = controllers.get(id);
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
        try {
            Parameter[] parameters = method.getParameters();
            int parameterCount = method.getParameterCount();
            List<Object> returned = new ArrayList<>();
            JsonNode treeNode = mapper.readTree(args);/*.traverse().readValueAsTree();*/

            if (!treeNode.isArray()) {
                throw new IncorrectJsonObjectException("Root node must be array");
            }

            int arraySize = treeNode.size();

            if (arraySize < parameterCount) {
                throw new IncorrectJsonObjectException("Method %s takes %d parameters");
            }

            if (arraySize > parameterCount && !parameters[parameterCount - 1].getType().isArray())
                throw new IncorrectJsonObjectException("Method %s takes %d parameters");

            int j = 0;
            for (int i = 0; i < arraySize; i++) {
                TreeNode parameter = treeNode.get(i);
                Class<?> clazz = parameters[j].getType();
                JsonParser parser = parameter.traverse();

                Object o = mapper.readValue(parser, clazz);

                returned.add(o);

                if (j < parameterCount - 1)
                    j++;
            }

            return returned.toArray();
        }
        catch (IOException e){
            throw new IncorrectJsonObjectException("Key parameters contains incorrect objects");
        }
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
