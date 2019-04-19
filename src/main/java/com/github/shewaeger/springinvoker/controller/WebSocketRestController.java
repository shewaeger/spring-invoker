package com.github.shewaeger.springinvoker.controller;

import com.github.shewaeger.springinvoker.dto.ControllerMethodInfoWrapper;
import com.github.shewaeger.springinvoker.service.WebSocketUpdatedHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebSocketRestController {

    @Autowired
    WebSocketUpdatedHandlerService service;

    @GetMapping("/ws/web-socket-api")
    public List<ControllerMethodInfoWrapper> getWSData() {
        return service.getMethodInfo();
    }

    @PostMapping("/ws/web-socket-api/{id}")
    public Object sendMessage(@PathVariable Long id, @RequestBody String wrapper) {
        return service.invokeMethod(id, wrapper);
    }

}
