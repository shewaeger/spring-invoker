package org.communis.websocket.tester.temp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dd")
public class WebSocketRestController {

    @Autowired
    WebSocketHandlerService service;

    @GetMapping
    List<WebSocketControllerInfo> test(){
        return service.getWebSocketInfo();
    }

}
