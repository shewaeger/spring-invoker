package com.github.shewaeger.springinvoker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSocketMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/ws/**")
                .addResourceLocations("classpath:/static/frontend/**");

        registry.addResourceHandler("/web-socket-tester.html")
                .addResourceLocations("classpath:/templates/web-socket-tester.html");
    }
}
