package org.communis.websocket.tester.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.Data;
import org.communis.websocket.tester.ClassMethodInfo;

import java.util.List;

@Data
public class WebSocketControllerMethodsInfoWrapper {

    private Long id;
    private String ownerName;
    private String name;
    private List<String> channels;
    private JsonSchema parameter;


    public WebSocketControllerMethodsInfoWrapper(Long id, ClassMethodInfo info, ObjectMapper mapper) {
        this.id = id;
        this.name = info.getMethod().getName();
        this.channels = info.getChannels();
        this.ownerName = info.getOwner().getName();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        try {
            this.parameter = generator.generateSchema(info.getParameter());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            this.parameter = null;
        }

    }


}
