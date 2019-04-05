package org.communis.websocket.tester;

import org.communis.websocket.tester.annotation.EnableWebSocketController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebSocketController(pkg = "org.communis.websocket.tester")
public class WebSocketTesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketTesterApplication.class, args);

	}

}
