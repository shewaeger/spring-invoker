package org.communis.websocket.tester.utils;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotations.WebSocketController;
import org.communis.websocket.tester.services.WebSocketHandlerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

@Log4j2
@Component
public class AppBeanDefinitionPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Lazy
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (!beanClass.isAnnotationPresent(WebSocketController.class) || !beanClass.isInterface())
            return null;

        Map<Method, List<String>> methodsChannel = new HashMap<>();
        Method[] methods = beanClass.getMethods();

        Arrays.asList(methods).forEach(method -> methodsChannel.put(method, NameGenerator.generateChannelFromMethod(method)));

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback((MethodInterceptor) (o, method, args, proxy) -> {
            methodsChannel.get(method).forEach(channel -> {
                messagingTemplate.convertAndSend(channel, args);
                log.info("sending message to channel {}", channel);
            });
            return null;

        });
        return enhancer.create();
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        return true;

    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


}
