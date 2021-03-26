package com.labis.rabbit;

import com.labis.gateway.User;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/")
public class ProducerController {

    private static final String queue = "user.queue";

    private static final String exchange = "user.exchange";

    private static final String routingKey = "user.routingkey";

    private RabbitMqSender rabbitMqSender;

    private String message = "Message has been sent succesfully";

    @Autowired
    public ProducerController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(myExchange())
                .with(routingKey)
                .noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @GetMapping(value = "user")
    public String publishUserDetails(@RequestBody User user) {
        System.out.println("user: '" + user + "' enviado.");
        this.rabbitMqSender.send(user);
        return message;
    }
}
