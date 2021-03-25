package com.labis.rabbit;

import com.labis.gateway.User;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/")
public class ProducerController {


    private RabbitMqSender rabbitMqSender;

    private String message = "Message has been sent succesfully";

    @Autowired
    public ProducerController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @GetMapping(value = "user")
    public String publishUserDetails(@RequestBody User user) {
        rabbitMqSender.send(user);
        return message;
    }
}
