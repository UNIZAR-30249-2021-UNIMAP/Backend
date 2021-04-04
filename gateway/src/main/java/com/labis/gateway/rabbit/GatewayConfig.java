package com.labis.gateway.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"gateway", "configuration"})
@Configuration
public class GatewayConfig {

    static final String queueName = "gateway.queue";

    @Bean
    public Queue queue(){return new Queue(queueName);}

    @Profile("receiver")
    @Bean
    public Receiver receiver(){
        return new Receiver();
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
