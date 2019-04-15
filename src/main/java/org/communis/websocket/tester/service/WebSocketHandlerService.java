package org.communis.websocket.tester.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.communis.websocket.tester.info.ControllerMethodInfo;
import org.communis.websocket.tester.annotation.WebSocketController;
import org.communis.websocket.tester.dto.WebSocketControllerMethodsInfoWrapper;
import org.communis.websocket.tester.exception.IncorrectJsonObjectException;
import org.communis.websocket.tester.exception.NotFoundException;
import org.communis.websocket.tester.exception.UnableToInvokeMethodException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class WebSocketHandlerService {

    private Map<Long, ControllerMethodInfo> beans = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {

        Map<String, Class<?>> beans = getAllClassesForAnnotation(WebSocketController.class);

        final Long[] index = {0L};
        beans.forEach((beanId, clazz) -> {
            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                this.beans.put(++index[0], new ControllerMethodInfo(method, clazz, beanId));
            }
        });

    }

    public List<WebSocketControllerMethodsInfoWrapper> getWebSocketInfo() {
        List<WebSocketControllerMethodsInfoWrapper> retValue = new ArrayList<>();
        beans.forEach((beanId, info) -> {
            retValue.add(new WebSocketControllerMethodsInfoWrapper(beanId, info, objectMapper));
        });
        return retValue;
    }

    public Boolean sendMessage(Long id, String jsonMessage, String user) {

        ControllerMethodInfo controllerMethodInfo = beans.get(id);
        if (controllerMethodInfo == null)
            throw new NotFoundException("Unable to find bean with id %s", id);

        Object bean = applicationContext.getBean(controllerMethodInfo.getBeanId());

        Object parameter;

        if(controllerMethodInfo.getIsParameterPrimitive()) {
            parameter = jsonMessage;
        }
        else {
            try {
                parameter = objectMapper.readValue(jsonMessage, controllerMethodInfo.getParameter());
            } catch (IOException e) {
                throw new IncorrectJsonObjectException(e, "Object %s is incorrect", jsonMessage);
            }
        }

        try {
            if (user == null)
                controllerMethodInfo.getMethod().invoke(bean, parameter);
            else
                controllerMethodInfo.getMethod().invoke(bean, user, parameter);
        } catch (Exception e) {
            throw new UnableToInvokeMethodException(e, "Unable to invoke method %s", controllerMethodInfo.getMethod().getName());
        }

        return true;
    }

    public Boolean sendMessage(Long id, String jsonMessage) {
        return sendMessage(id, jsonMessage, null);
    }

    //Не создавая бины, инфу о них можно получить так:
    private Map<String, Class<?>> getAllClassesForAnnotation(Class<? extends Annotation> annotation) {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(annotation);
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
