package org.communis.websocket.tester.temp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BrigadeService {

    @Autowired
    BrigadeWSController controller;

    @Scheduled(fixedDelay = 1000)
    public void call(){
        controller.sendToBrigade(new BrigadeWrapper());
    }

}
