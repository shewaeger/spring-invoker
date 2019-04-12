package org.communis.websocket.tester.util;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotation.WebSocketController;
import org.communis.websocket.tester.configuration.WebSocketMvcConfig;
import org.communis.websocket.tester.controller.WebSocketRestController;
import org.communis.websocket.tester.controller.WebSocketWebController;
import org.communis.websocket.tester.service.WebSocketHandlerService;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log4j2
public class WebSocketBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private static final List<Class<?>> pkgBeans = new ArrayList<>(Arrays.asList(
            WebSocketBeanDefinitionPostProcessor.class,
            WebSocketRestController.class,
            WebSocketHandlerService.class,
            WebSocketWebController.class,
            WebSocketMvcConfig.class
    ));

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

        //pkgBeans.forEach(c -> registerBeans(registry, c));

    }

    private void registerBeans(BeanDefinitionRegistry registry, Class<?> clazz){
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(clazz);
        if(!registry.isBeanNameInUse(clazz.getName())) {
            registry.registerBeanDefinition(clazz.getName(), genericBeanDefinition);
        }

    }
}
