package org.communis.websocket.tester.temp.entity;

import org.communis.websocket.tester.annotations.WSController;
import org.communis.websocket.tester.annotations.WSSendTo;

public interface BrigadeWSController extends WSController {

    @WSSendTo("/queue/brigade")
    void sendToBrigade(BrigadeWrapper wrapper);

}
