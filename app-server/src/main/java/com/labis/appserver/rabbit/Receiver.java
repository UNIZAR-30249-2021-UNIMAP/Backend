package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "appserver.queue")
    public void receiveMessage(String message) {
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");

    }

}