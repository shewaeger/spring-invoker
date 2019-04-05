package org.communis.websocket.tester.annotation;

import org.communis.websocket.tester.bpp.TestBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestBeanDefinitionRegistrar.class)
public @interface EnableWebSocketController {
    String pkg();
}
