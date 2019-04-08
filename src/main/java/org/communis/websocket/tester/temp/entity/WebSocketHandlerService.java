package org.communis.websocket.tester.temp.entity;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebSocketHandlerService {

    private List<Class<?>> beans;

    public void setBeans(List<Class<?>> beans) {
        this.beans = beans;
    }

    public List<WebSocketControllerInfo> getWebSocketInfo() {
        return beans.stream().map(WebSocketControllerInfo::new).collect(Collectors.toList());

    }
}
