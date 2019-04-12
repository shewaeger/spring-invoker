package org.communis.websocket.tester.configuration;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
public class WebSocketMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/web-socket-api/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*");
    }

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
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver((viewName, locale) -> new View() {

            @Override
            public String getContentType() {
                return "text/html";
            }

            @Override
            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                ClassPathResource classPathResource = new ClassPathResource("/templates/" + viewName + ".html");
                IOUtils.copy(classPathResource.getInputStream(), response.getOutputStream());
                response.setContentType("text/html");
            }
        });
    }
}
