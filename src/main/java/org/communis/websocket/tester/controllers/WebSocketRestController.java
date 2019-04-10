package org.communis.websocket.tester.controllers;

import org.communis.websocket.tester.dto.WebSocketControllerMethodsInfoWrapper;
import org.communis.websocket.tester.services.WebSocketHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web-socket-api")
public class WebSocketRestController {

    @Autowired
    WebSocketHandlerService service;

    @GetMapping
    List<WebSocketControllerMethodsInfoWrapper> getWSData(){
        return service.getWebSocketInfo();
    }

    @PostMapping("{id}")
    Boolean sendMessage(@PathVariable Long id, @RequestBody String jsonObject) {
        return service.sendMessage(id, jsonObject);
    }

    @PostMapping("{id}/{user}")
    Boolean sendMessageForUser(@PathVariable Long id, @PathVariable String user, @RequestBody String jsonObject){
        return service.sendMessage(id, jsonObject, user);
    }
}
