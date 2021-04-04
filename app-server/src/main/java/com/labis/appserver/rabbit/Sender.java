package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Sender {
    @Autowired
    private RabbitTemplate template;

    //@Autowired
    //private DirectExchange direct;

    static final String queueName = "gateway.queue";

    static final String routingKey = "foo.bar.baz";

    public void send() {
        String message = "Hello World!";
        //this.template.convertAndSend(direct.getName(), routingKey, message);
        template.convertAndSend(queueName, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
