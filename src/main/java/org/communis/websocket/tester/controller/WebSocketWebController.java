package org.communis.websocket.tester.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@RequestMapping("/ws/")
public class WebSocketWebController extends WebMvcConfigurerAdapter {

    @GetMapping("web-socket-tester")
    public String main() {
        return "web-socket-tester";
    }

}