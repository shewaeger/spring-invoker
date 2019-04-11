package org.communis.websocket.tester.temp.entity;

import org.communis.websocket.tester.annotations.WebSocketController;

@WebSocketController
public interface UserWSController {

    void sendToQueueUser(String user, String message);

}
