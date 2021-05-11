package com.labis.appserver.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.labis.appserver.controllers.HolaMundo;

import java.util.ArrayList;

import static com.labis.appserver.model.Constantes.*;

public class Receiver {

    @Autowired
    private JdbcTemplate jdbc;

    private HolaMundo holamundo;

    @RabbitListener(queues = "tut.rpc.requests")
    // @SendTo("tut.rpc.replies") used when the
    // client doesn't set replyTo.
    public String receiveMessage(ArrayList<String> message) {
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");

        this.holamundo = new HolaMundo();
        String guarda = message.get(0);
        message.remove(0);
        switch (guarda) {
            case STRING_USERS:
                System.out.println("mensaje de vuelta---->");
                String out = holamundo.saludo(message);
                return "[{\"mensaje users\": \"" + out + "\"}]";

            case STRING_INCIDENCIA:
                out = holamundo.incidencia(message);
                return "[{\"mensaje incidencia\": \"" + out + "\"}]";
            default:
                return "Error";
        }
    }

}