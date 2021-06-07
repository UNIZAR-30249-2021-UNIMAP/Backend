package com.labis.appserver.rabbit;

import com.labis.appserver.service.IncidenciaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static com.labis.appserver.common.Constantes.*;

public class Receiver {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    IncidenciaService incidenciaService;

    @RabbitListener(queues = "tut.rpc.requests")
    public String receiveMessage(ArrayList<String> message) {
        System.out.println("Received in 'appserver/Receiver' <" + message + ">");

        String guarda = message.remove(0);
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
                long idIncidencia = Long.parseLong(message.remove(0));
                boolean aceptar = Boolean.parseBoolean(message.remove(0));
                long idEmpleado = Long.parseLong(message.remove(0));
                String prioridad = message.remove(0);

                boolean resultado = incidenciaService.aceptarORechazarIncidencia(idIncidencia, aceptar, idEmpleado, prioridad);

                if (resultado) {
                    return STRING_STATUS_OK;
                } else {
                    return STRING_STATUS_ERROR;
                }

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