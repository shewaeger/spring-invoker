package org.communis.websocket.tester.temp.entity;

import org.communis.websocket.tester.annotations.WSController;
import org.communis.websocket.tester.annotations.WSSendTo;

@WSController
public interface BrigadeWSController {

    @WSSendTo("/queue/brigade")
    void sendToBrigade(BrigadeWrapper wrapper);

}
