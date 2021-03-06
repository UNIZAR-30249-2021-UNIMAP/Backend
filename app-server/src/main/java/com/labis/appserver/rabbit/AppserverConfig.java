package com.labis.appserver.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"appserver", "configuration"})
@Configuration
public class AppserverConfig {

    @Profile("receiver")
    private static class ReceiverConfig{
        @Bean
        public Queue queue() {
            return new Queue("tut.rpc.requests");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange,
                               Queue queue) {
            return BindingBuilder.bind(queue)
                    .to(exchange)
                    .with("rpc");
        }
        @Bean
        public Receiver receiver(){
            return new Receiver();
        }
    }


    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
