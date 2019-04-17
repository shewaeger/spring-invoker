package com.github.shewaeger.websocket.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.shewaeger.websocket.dto.ParameterWrapper;
import com.github.shewaeger.websocket.exception.IncorrectJsonObjectException;
import org.springframework.util.ClassUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ParameterDeserializer extends JsonDeserializer<List<ParameterWrapper>> {

    private Method method;

    public ParameterDeserializer(@NotNull Method method){
        this.method = method;
    }
    @Override
    public List<ParameterWrapper> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Parameter[] parameters = method.getParameters();
        int parameterCount = method.getParameterCount();
        ObjectCodec codec = p.getCodec();
        TreeNode treeNode = codec.readTree(p);
        //p.tre
        int arraySize = treeNode.size();

        if(!treeNode.isArray()) {
            throw new IncorrectJsonObjectException("Root node must be array");
        }

        if(arraySize < parameterCount){
            throw new IncorrectJsonObjectException("Method %s takes %d parameters");
        }

        if(arraySize > parameterCount && !parameters[parameterCount -1].getType().isArray())
            throw new IncorrectJsonObjectException("Method %s takes %d parameters");

        int j = 0;
        for (int i = 0; i < arraySize; i++) {
            TreeNode parameter = treeNode.get(i);
            TreeNode value = parameter.get("value");
            Class<?> clazz = parameters[j].getType();

            if(ClassUtils.isPrimitiveOrWrapper(clazz)){

            }

            if(j < parameterCount -1)
                j++;
        }

        return null;
    }
}
