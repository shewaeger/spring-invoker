package org.communis.websocket.tester.annotations;

import org.communis.websocket.tester.bean.utilitys.AppImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AppImportBeanDefinitionRegistrar.class)
public @interface EnableWebSocketControllers {
}
