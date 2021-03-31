package com.labis.appserver;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Controller
public class ProducerController {
    static final String topicExchangeName = "gatewayExchange";

    static final String sendingQueueName = "gatewayQueue";
    static final String receivingQueueName = "gatewayQueue";

    static final String routingKey = "foo.bar.baz";

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;


    public ProducerController(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    Queue queue() {
        return new Queue(sendingQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("bar.foo.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(receivingQueueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public String publishUserDetails() throws InterruptedException {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, "Hello from appserver!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return "Ok";
    }
}
