package org.communis.websocket.tester.interfaces;

import org.communis.websocket.tester.annotation.WebSocketSendTo;
import org.communis.websocket.tester.dto.BrigadeWrapper;

public interface BrigadeWsController extends WSController {

    @WebSocketSendTo("/queue")
    void sendToBrigade(BrigadeWrapper wrapper);

}
