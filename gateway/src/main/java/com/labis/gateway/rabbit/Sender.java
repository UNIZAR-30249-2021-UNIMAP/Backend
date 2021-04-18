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

    static final String directExchangeName = "tut.rpc";

    @GetMapping(value = "user")
    public void publishUserDetails() {
        System.out.println("Sending message...");
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", "Hello from gateway!");
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
    }
}
