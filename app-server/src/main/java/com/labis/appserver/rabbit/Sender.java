package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Sender {
    @Autowired
    private RabbitTemplate template;

    static final String queueName = "gateway.queue";

    public void send() {
        String message = "Hello World!";
        template.convertAndSend(queueName, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
