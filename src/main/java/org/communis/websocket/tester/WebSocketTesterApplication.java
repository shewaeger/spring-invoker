package org.communis.websocket.tester;

import org.communis.websocket.tester.annotations.EnableWebSocketControllers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableWebSocketControllers
@EnableScheduling
public class WebSocketTesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketTesterApplication.class, args);
	}

}
