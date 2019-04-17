package com.github.shewaeger.websocket.bean;

import lombok.extern.log4j.Log4j2;
import com.github.shewaeger.websocket.annotation.WebSocketController;
import com.github.shewaeger.websocket.exception.MethodHasInvalidParametersException;
import com.github.shewaeger.websocket.util.BeanUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
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

            // check method parameters
            int parameterCount = method.getParameterCount();
            Class<?> parameterType;
            Class<?> userField = null;
            if(parameterCount == 1) {
                parameterType = method.getParameterTypes()[0];
            }
            else if(parameterCount == 2) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                parameterType = parameterTypes[1];
                userField = parameterTypes[0];
            }
            else{
                throw new MethodHasInvalidParametersException(
                        "Method %s has %d parameters. Method must contain one or two parameters." +
                                " If the method contains two parameters, the first must be of type java.lang.String.",
                        method.getName(), method.getParameterCount()
                );
            }

            if(userField != null && !userField.equals(String.class)){
                throw new MethodHasInvalidParametersException(
                        "Method %s must have a first argument as java.lang.String.",
                        method.getName()
                );
            }

            if(BeanUtils.isClassPrimitive(parameterType) && !parameterType.equals(String.class)){
                throw new MethodHasInvalidParametersException(
                        "method %s should not have primitive types as %s",
                        method.getName(), parameterType.getName()
                );
            }

        }

    }
}
