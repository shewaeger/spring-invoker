package org.communis.websocket.tester.temp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.Data;
import org.communis.websocket.tester.bean.utilitys.NameGenerator;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class WebSocketControllerMethodsInfo {

    private String name;
    private List<String> queue;
    private JsonSchema parameter;


    public WebSocketControllerMethodsInfo(Method method) {
        this.name = method.getName();
        this.queue = NameGenerator.generateChannelFromMethod(method);
        Class <?> parameterClass = method.getParameterTypes()[0];
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);

        try {
            parameter = generator.generateSchema(parameterClass);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            parameter = null;
        }

    }


}
