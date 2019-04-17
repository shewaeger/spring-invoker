package com.github.shewaeger.websocket.controller;

import com.github.shewaeger.websocket.dto.ControllerMethodInfoWrapper;
import com.github.shewaeger.websocket.service.WebSocketUpdatedHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebSocketRestController {

    @Autowired
    WebSocketUpdatedHandlerService service;
    //WebSocketHandlerService service;

    @GetMapping("/ws/web-socket-api")
    public List<ControllerMethodInfoWrapper> getWSData() {
        //return service.getWebSocketInfo();
        return service.getMethodInfo();
    }

    @PostMapping("/ws/web-socket-api/{id}")
    public Object sendMessage(@PathVariable Long id, @RequestBody String jsonObject) {
        return service.invokeMethod(id, jsonObject);
    }

}
