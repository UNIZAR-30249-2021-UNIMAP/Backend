package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "tut.rpc.requests")
    // @SendTo("tut.rpc.replies") used when the
    // client doesn't set replyTo.
    public String receiveMessage(String message) {
        String msg = "Hello from appserver!";
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");
        return msg;
    }

}