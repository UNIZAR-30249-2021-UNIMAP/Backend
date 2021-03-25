package com.labis.gateway;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

//    @Value("${spring.rabbitmq.host}")
//    String host;
//
//    @Value("${spring.rabbitmq.port}")
//    String port;
//
//    @Value("${spring.rabbitmq.username}")
//    String username;
//
//    @Value("${spring.rabbitmq.password}")
//    String password;


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /*@Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, Integer.parseInt(port));
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }*/




//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate();
//
//        return rabbitTemplate;
//    }
}
