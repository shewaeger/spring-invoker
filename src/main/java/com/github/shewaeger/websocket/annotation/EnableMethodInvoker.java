package com.github.shewaeger.websocket.annotation;

import com.github.shewaeger.websocket.configuration.ScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({/*WebSocketBeanDefinitionRegistrar.class,*/ ScanConfiguration.class})
public @interface EnableMethodInvoker {
}
