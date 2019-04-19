package com.github.shewaeger.springinvoker.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.shewaeger.springinvoker.info.ControllerMethodInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Log4j2
public class ControllerMethodInfoWrapper {

    private List<ParameterWrapper> parameters = new ArrayList<>();

    private String name;

    private String idOwner;

    private Long id;

    private String description;

    public ControllerMethodInfoWrapper(Long id, ControllerMethodInfo methodInfo, ObjectMapper mapper) {
        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        String[] parameterNames = discoverer.getParameterNames(methodInfo.getMethod());
        Parameter[] parameters = methodInfo.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            try {
                this.parameters.add(
                        new ParameterWrapper(parameterNames[i], generator.generateSchema(parameters[i].getType()))
                );
            } catch (JsonMappingException e) {
                log.warn("Unable to create method info: {0}", e);
            }
        }
        this.id = id;
        this.name = methodInfo.getMethod().getName();
        this.idOwner = methodInfo.getOwner().getName();
        this.description = methodInfo.getDescription();
    }

}
