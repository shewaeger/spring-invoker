package org.communis.websocket.tester.temp.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Data
public class WebSocketControllerInfo {

    String name;

    List<WebSocketControllerMethodsInfo> methods = new ArrayList<>();

    public WebSocketControllerInfo(Class<?> controller){
        this.name = controller.getTypeName();

        for (Method method : controller.getMethods()) {
            this.methods.add(new WebSocketControllerMethodsInfo(method));
        }
    }

}
