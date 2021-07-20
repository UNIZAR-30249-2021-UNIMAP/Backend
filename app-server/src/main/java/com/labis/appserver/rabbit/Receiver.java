package com.labis.appserver.rabbit;

import com.google.gson.Gson;
import com.labis.appserver.AppServerApplication;
import com.labis.appserver.service.IncidenciaService;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static com.labis.appserver.common.Constantes.*;

public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

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
                log.info("dentro de incidencia");
                String json_respuesta = JSONArray.toJSONString(incidenciaService.findAll());;
                log.info("JSON: " + json_respuesta);
                return json_respuesta;

            case STRING_INCIDENCIA_REPORTE:
                Long idEspacio = Long.parseLong(message.remove(0));
                String descripcion = message.remove(0);
                String email = message.remove(0);
                String imagen = message.remove(0);
                incidenciaService.reportarIncidencia(idEspacio, descripcion, email, imagen);

                return STRING_STATUS_OK;

            case STRING_REGISTRO:
                return "registro";

            case STRING_INCIDENCIA_MANTENIMIENTO:
                return "incidencia mantenimiento";

            case STRING_INCIDENCIA_ADMIN:
                long idIncidencia = Long.parseLong(message.remove(0));
                boolean aceptar = Boolean.parseBoolean(message.remove(0));
                long idEmpleado = 0;
                if (!message.get(0).equals("")) {
                    idEmpleado = Long.parseLong(message.remove(0));
                } else {
                    message.remove(0); //Elimina el hueco nulo del array
                }
                String prioridad = message.remove(0);
                String motivo = message.remove(0);

                boolean resultado = incidenciaService.aceptarORechazarIncidencia(idIncidencia, aceptar,
                        idEmpleado, prioridad, motivo);

                log.info("Resultado final:" + resultado);
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