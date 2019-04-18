package com.github.shewaeger.websocket.dto;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParameterWrapper {
    private String name;
    private JsonSchema schema;

    ParameterWrapper(String name, JsonSchema schema){
        this.name = name;
        this.schema = schema;
    }

}
