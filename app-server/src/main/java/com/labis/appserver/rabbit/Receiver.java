package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Receiver {

    @Autowired
    private JdbcTemplate jdbc;

    @RabbitListener(queues = "tut.rpc.requests")
    // @SendTo("tut.rpc.replies") used when the
    // client doesn't set replyTo.
    public String receiveMessage(String message) {
        String msg = "Hello from appserver!";
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");

        try {
            System.out.println("RESULTADO-------->");
            System.out.println(jdbc.queryForObject("select count(*) from adap00",
                    Long.class));
            System.out.println("<--------");
        } catch (Exception e) {
            System.out.println("-------------When counting: " + e);
        }

        return msg;
    }

}