package org.communis.websocket.tester.bean.utilitys;

import lombok.extern.log4j.Log4j2;
import org.communis.websocket.tester.annotations.WSController;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

@Log4j2
public class AppImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        String className = importingClassMetadata.getClassName();
        int pkgNameEnd = className.lastIndexOf(".");
        String pkgName = className.substring(0, pkgNameEnd);

        Reflections reflections = new Reflections(pkgName, new SubTypesScanner());
        Set<Class<? extends WSController>> controllers = reflections.getSubTypesOf(WSController.class);

        for (Class<? extends WSController> controller : controllers) {

            if (!controller.isInterface())
                continue;

            log.info("Found WSControllers {}", controller.getName());
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setBeanClass(controller);
            registry.registerBeanDefinition(controller.getName(), genericBeanDefinition);

        }

    }
}
