package com.labis.gateway.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "gateway.queue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}