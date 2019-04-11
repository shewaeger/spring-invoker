package org.communis.websocket.tester.temp.entity;

import org.communis.websocket.tester.annotations.WebSocketController;
import org.springframework.messaging.handler.annotation.SendTo;

@WebSocketController
public interface UserWSController {

    @SendTo("/queue/user/test")
    void userTest(UserEntity user);

    void sendToQueueUser(String user, String message);

}
