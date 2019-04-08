package org.communis.websocket.tester.bean.utilitys;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotations.WSController;
import org.communis.websocket.tester.annotations.WSSendTo;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

@Log4j2
@Component
public class AppBeanDefinitionPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Lazy
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        Class<?>[] interfaces = beanClass.getInterfaces();
        if (!Arrays.asList(interfaces).contains(WSController.class))
            return null;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback((MethodInterceptor) (o, method, args, proxy) -> {
            String channel = method.getAnnotation(WSSendTo.class).value();
            log.info("Sending message for channel {}", channel);
            messagingTemplate.convertAndSend(channel, args);
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
