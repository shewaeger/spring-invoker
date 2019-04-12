package org.communis.websocket.tester.annotation;

import org.communis.websocket.tester.configuration.ScanConfiguration;
import org.communis.websocket.tester.util.WebSocketBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WebSocketBeanDefinitionRegistrar.class, ScanConfiguration.class})
public @interface EnableWebSocketControllers {
}
