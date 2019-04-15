package org.communis.websocket.tester.bean;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotation.WebSocketController;
import org.communis.websocket.tester.configuration.WebSocketMvcConfig;
import org.communis.websocket.tester.controller.WebSocketRestController;
import org.communis.websocket.tester.controller.WebSocketWebController;
import org.communis.websocket.tester.exception.MethodHasInvalidParametersException;
import org.communis.websocket.tester.service.WebSocketHandlerService;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log4j2
public class WebSocketBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        String className = importingClassMetadata.getClassName();
        int pkgNameEnd = className.lastIndexOf(".");
        String pkgName = className.substring(0, pkgNameEnd);

        Reflections reflections = new Reflections(pkgName,new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(WebSocketController.class);

        for (Class<?> controller : controllers) {
            if (!controller.isInterface())
                continue;
            log.info("Found WSControllers {}", controller.getName());
            registerBeans(registry, controller);
        }
    }

    private void registerBeans(BeanDefinitionRegistry registry, Class<?> clazz){

        validateClass(clazz);

        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(clazz);
        if(!registry.isBeanNameInUse(clazz.getName())) {
            registry.registerBeanDefinition(clazz.getName(), genericBeanDefinition);
        }
    }

    private void validateClass(Class<?> clazz){
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            int parameterCount = method.getParameterCount();
            if (parameterCount == 1 || parameterCount == 2) {
                if (parameterCount == 1)
                    continue; //don`t throw exception
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes[0].equals(String.class))
                    continue; //don`t throw exception
            }

            throw new MethodHasInvalidParametersException(
                    "Method %s has %d parameters. Method must contain one or two parameters." +
                            " If the method contains two parameters, the first must be of type %s",
                    method.getName(), method.getParameterCount(), String.class.getName()
            );
        }

    }
}
