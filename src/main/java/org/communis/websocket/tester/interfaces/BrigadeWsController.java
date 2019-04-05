package org.communis.websocket.tester.interfaces;

import org.communis.websocket.tester.dto.BrigadeWrapper;

public interface BrigadeWsController extends WSController {

    void sendToBrigade(BrigadeWrapper wrapper);

}
