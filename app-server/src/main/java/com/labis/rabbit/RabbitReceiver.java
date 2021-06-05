package com.labis.rabbit;

import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

}
