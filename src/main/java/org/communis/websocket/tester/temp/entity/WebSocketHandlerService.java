package org.communis.websocket.tester.temp.entity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebSocketHandlerService {

    private Map<Class<?>, String> beans;

    public void setBeans(Map<Class<?>, String> beans) {
        this.beans = beans;
    }

    public List<WebSocketControllerInfo> getWebSocketInfo() {
        //return beans.stream().map(WebSocketControllerInfo::new).collect(Collectors.toList());
        List<WebSocketControllerInfo> retValue = new ArrayList<>();
        beans.forEach((clazz, bean)-> {
            retValue.add(new WebSocketControllerInfo(clazz));
        });
        return retValue;
    }
}
