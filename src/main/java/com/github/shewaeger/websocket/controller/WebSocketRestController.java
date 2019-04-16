package com.github.shewaeger.websocket.controller;

import com.github.shewaeger.websocket.dto.WebSocketControllerMethodsInfoWrapper;
import com.github.shewaeger.websocket.service.WebSocketHandlerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class WebSocketRestController {

    @Autowired
    WebSocketHandlerService service;

    @GetMapping("/ws/web-socket-api")
    public List<WebSocketControllerMethodsInfoWrapper> getWSData() {
        log.info(service);
        return service.getWebSocketInfo();
    }

    @PostMapping("/ws/web-socket-api/{id}")
    public Boolean sendMessage(@PathVariable Long id, @RequestBody String jsonObject) {
        return service.sendMessage(id, jsonObject);
    }

    @PostMapping("/ws/web-socket-api/{id}/{user}")
    public Boolean sendMessageForUser(@PathVariable Long id, @PathVariable String user, @RequestBody String jsonObject) {
        return service.sendMessage(id, jsonObject, user);
    }
}
