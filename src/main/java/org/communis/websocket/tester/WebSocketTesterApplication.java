package org.communis.websocket.tester;

import org.communis.websocket.tester.annotations.EnableWebSocketControllers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSocketControllers
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketTesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketTesterApplication.class, args);
	}

}
