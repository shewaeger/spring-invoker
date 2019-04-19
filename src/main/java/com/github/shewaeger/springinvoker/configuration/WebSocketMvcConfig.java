package com.github.shewaeger.springinvoker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSocketMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/ws/js/**")
                .addResourceLocations("classpath:/static/frontend/js/");

        registry.addResourceHandler("/ws/css/**")
                .addResourceLocations("classpath:/static/frontend/css/");

        registry.addResourceHandler("/ws/fonts/**")
                .addResourceLocations("classpath:/static/frontend/fonts/");

        registry.addResourceHandler("/ws/favicon.ico")
                .addResourceLocations("classpath:/static/frontend/favicon.ico");

        registry.addResourceHandler("/spring-invoker.html")
                .addResourceLocations("classpath:/templates/spring-invoker.html");
    }
}
