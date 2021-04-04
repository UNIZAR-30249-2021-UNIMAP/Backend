package com.labis.gateway.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/")
public class Sender {
    @Autowired
    private RabbitTemplate template;

    static final String queueName = "appserver.queue";

    static final String routingKey = "bar.foo.baz";

    @GetMapping(value = "user")
    public String publishUserDetails() throws InterruptedException {
        System.out.println("Sending message...");
        //template.convertAndSend(direct.getName(), routingKey, "Hello from gateway!");
        template.convertAndSend(queueName, "Hello from gateway!");
        return "Ok";
    }
}
