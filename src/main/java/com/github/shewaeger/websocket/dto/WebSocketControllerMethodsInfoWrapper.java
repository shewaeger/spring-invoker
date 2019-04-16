package com.github.shewaeger.websocket.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.Data;
import com.github.shewaeger.websocket.info.ControllerMethodInfo;

import java.util.List;

@Data
public class WebSocketControllerMethodsInfoWrapper {

    private Long id;
    private String ownerName;
    private String name;
    private List<String> channels;
    private JsonSchema parameter;
    private Boolean hasUser;


    public WebSocketControllerMethodsInfoWrapper(Long id, ControllerMethodInfo info, ObjectMapper mapper) {
        this.id = id;
        this.name = info.getMethod().getName();
        this.channels = info.getChannels();
        this.ownerName = info.getOwner().getName();
        this.hasUser = info.getHasUser();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        try {
            this.parameter = generator.generateSchema(info.getParameter());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            this.parameter = null;
        }

    }


}
