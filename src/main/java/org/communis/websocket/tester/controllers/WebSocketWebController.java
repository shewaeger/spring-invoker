package org.communis.websocket.tester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketWebController {

    @GetMapping("/")
    public String webSocketTester(Model model){
        model.addAttribute("eventName", "FIFA 2018");
        return "web-socket-tester";
    }

}
