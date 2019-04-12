package org.communis.websocket.tester.util;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotation.WebSocketController;
import org.communis.websocket.tester.exception.MethodHasInvalidParameters;
import org.communis.websocket.tester.info.ReflectionMethodInfo;
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
public class WebSocketBeanDefinitionPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Lazy
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (!beanClass.isAnnotationPresent(WebSocketController.class) || !beanClass.isInterface())
            return null;
        log.info("Configuring bean: {}", beanName);
        Map<Method, ReflectionMethodInfo> methodInfoMap = new HashMap<>();
        //Что бы не использовать reflection в лямбде, заранее готовлю всю информацию.
        Method[] methods = beanClass.getMethods();
        for (Method method : methods) {
            List<String> queues = NameGenerator.generateChannelFromMethod(method);
            Boolean hasUser = methodHasUserField(method);
            if (hasUser == null)
                throw new MethodHasInvalidParameters(
                        "Method %s has %d parameters. Method must The method must contain one or two parameters." +
                                " If the method contains two parameters, the first must be of type %s",
                        method.getName(), method.getParameterCount(), String.class.getName()
                );
            ReflectionMethodInfo methodInfo = new ReflectionMethodInfo(queues, hasUser);
            methodInfoMap.put(method, methodInfo);
        }


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback((MethodInterceptor) (o, method, args, proxy) -> {
            ReflectionMethodInfo methodInfo = methodInfoMap.get(method);

            if (methodInfo.getForUser()) {
                for (String queue : methodInfo.getQueues()) {
                    messagingTemplate.convertAndSendToUser(args[0].toString(), queue, args[1]);
                }
            } else {
                for (String queue : methodInfo.getQueues()) {
                    messagingTemplate.convertAndSend(queue, args[0]);
                }
            }
            log.debug("Method {} has been invoking", method.getName());
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

    //Вернет null если неверное количество параметров.
    //Параметров может быть один, или два. Если два, то это значит, сообщение предназначено пользователю.
    //TODO hardcode
    private Boolean methodHasUserField(Method method) {
        int parameterCount = method.getParameterCount();
        if (parameterCount < 1 || parameterCount > 2) {
            return null;
        }

        if (parameterCount == 1)
            return false;

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes[0].equals(String.class))
            return true;

        return null;
    }


}
