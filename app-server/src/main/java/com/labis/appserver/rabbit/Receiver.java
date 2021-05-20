package com.labis.appserver.rabbit;

import com.labis.appserver.common.Constantes;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static com.labis.appserver.common.Constantes.*;

public class Receiver {

    @Autowired
    private JdbcTemplate jdbc;

    @RabbitListener(queues = "tut.rpc.requests")
    public String receiveMessage(ArrayList<String> message) {
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");

        String guarda = message.get(0);
        message.remove(0);
        switch (guarda) {
            case STRING_LOGIN:
                return "login";

            case STRING_INCIDENCIA:
                return "incidencia";

            case STRING_REGISTRO:
                return "registro";

            case STRING_INCIDENCIA_MANTENIMIENTO:
                return "incidencia mantenimiento";

            case STRING_INCIDENCIA_ADMIN:
                return "Incidencia admin";

            case STRING_MANTENIMIENTO:
                return "Mantenimiento";

            case STRING_ESPACIO:
                return "Espacio";

            case STRING_AFORO:
                return "Aforo";
                
            case STRING_ESPACIOS:
                return "Espacios";

            default:
                return "Error";
        }
    }

}