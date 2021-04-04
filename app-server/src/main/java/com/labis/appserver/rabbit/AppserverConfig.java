package com.labis.appserver.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"appserver", "configuration"})
@Configuration
public class AppserverConfig {

    static final String queueName = "appserver.queue";

    @Bean
    Queue queue(){return new Queue(queueName);}

    @Profile("receiver")
    @Bean
    Receiver receiver(){
        return new Receiver();
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
