package com.labis.rabbit;

import com.labis.gateway.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final String exchange = "user.exchange";

    private static final String routingkey = "user.routingkey";

    public void send(User user){
        this.rabbitTemplate.convertAndSend(exchange, routingkey, user);
        System.out.println("user: '" + user + "' enviado en MqSender.");
    }
}
