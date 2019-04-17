package com.github.shewaeger.websocket.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.shewaeger.websocket.info.UpdatedControllerMethodInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Log4j2
public class ControllerMethodInfoWrapper {

    List<JsonSchema> parameters = new ArrayList<>();

    String name;

    String idOwner;

    Long id;

    public ControllerMethodInfoWrapper(Long id, UpdatedControllerMethodInfo methodInfo, ObjectMapper mapper){
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        for (Class<?> parameter : methodInfo.getParameters()) {
            try {
                this.parameters.add(generator.generateSchema(parameter));
            } catch (JsonMappingException e) {
                log.warn("Unable to create method info: {0}", e);
            }
        }
        this.id = id;
        this.name = methodInfo.getMethod().getName();
        this.idOwner = methodInfo.getOwner().getName();
    }

}
