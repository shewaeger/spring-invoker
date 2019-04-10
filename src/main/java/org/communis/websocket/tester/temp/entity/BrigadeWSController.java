package org.communis.websocket.tester.temp.entity;

import org.communis.websocket.tester.annotations.WebSocketController;
import org.springframework.messaging.handler.annotation.SendTo;

@WebSocketController
public interface BrigadeWSController {

    void sendToQueueBrigadeExit(BrigadeWrapper wrapper);

    @SendTo("/queue/brigade")
    void sendToBrigade(BrigadeWrapper wrapper);

    void sendToQueueBrigadeTest(String user, BrigadeWrapper wrapper);

}
