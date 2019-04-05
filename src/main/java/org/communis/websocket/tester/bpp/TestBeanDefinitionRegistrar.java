package org.communis.websocket.tester.bpp;

import org.communis.websocket.tester.annotation.EnableWebSocketController;
import org.communis.websocket.tester.annotation.WebSocketSendTo;
import org.communis.websocket.tester.interfaces.WSController;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class TestBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableWebSocketController.class.getName());

        String pkg = attributes.get("pkg").toString();

        Reflections reflections = new Reflections(pkg, new SubTypesScanner());
        Set<Class<? extends WSController>> controllers = reflections.getSubTypesOf(WSController.class);

        for (Class<? extends WSController> controller : controllers) {
            Method[] methods = controller.getMethods();
            WebSocketSendTo annotation = methods[0].getAnnotation(WebSocketSendTo.class);
            String value = annotation.value();
            System.out.println("value: " + value);
            int i = methods.length;
        }
    }
}
