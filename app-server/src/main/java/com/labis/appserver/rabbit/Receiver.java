package com.labis.appserver.rabbit;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.service.IncidenciaService;
import com.labis.appserver.service.PersonaService;
import com.labis.appserver.service.PersonalMantenimientoService;
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

    @Autowired
    PersonalMantenimientoService personalMantenimientoService;

    @Autowired
    PersonaService personaService;

    @RabbitListener(queues = "tut.rpc.requests")
    public String receiveMessage(ArrayList<String> message) {
        log.info("Received in 'appserver/Receiver' <" + message + ">");

        String guarda = message.remove(0);
        switch (guarda) {
            case STRING_LOGIN:
                String email = message.remove(0);
                String contrasena = message.remove(0);
                int tipoLogin = personaService.loginPersona(email, contrasena);
                if (tipoLogin == TIPO_USUARIO_NO_EXISTE) {
                    log.info(email + " intento de login fallido");
                }
                return Integer.toString(tipoLogin);

            case STRING_REGISTRO:
                email = message.remove(0);
                String nombre = message.remove(0);
                contrasena = message.remove(0);
                boolean exitoRegistro = personaService.registrarPersona(email, nombre, contrasena);
                if (exitoRegistro) {
                    log.info(email + " registrado");
                    return STRING_STATUS_OK;
                } else {
                    log.info("El email '" + email + "' ya esta registrado");
                    return STRING_STATUS_ERROR;
                }

            case STRING_INCIDENCIA:
                return incidenciaService.informeTodasIncidencias().toJSONString();

            case STRING_INCIDENCIA_REPORTE:
                String idEspacio = message.remove(0);
                String descripcion = message.remove(0);
                email = message.remove(0);
                String imagen = message.remove(0);
                incidenciaService.reportarIncidencia(idEspacio, descripcion, email, imagen);

                return STRING_STATUS_OK;

            case STRING_INCIDENCIA_MANTENIMIENTO:
                String tipoPeticion = message.remove(0);
                if (tipoPeticion.equals("GET")) {
                    // Get: Incidencias de un empleado
                    long idPersonalMantenimiento = Long.parseLong(message.remove(0));
                    return personalMantenimientoService.incidenciasEmpleado(idPersonalMantenimiento).toJSONString();
                } else if (tipoPeticion.equals("POST")) {
                    //Post: finalizar incidencia
                    long IdIncidencia = Long.parseLong(message.remove(0));
                    incidenciaService.finalizarIncidencia(IdIncidencia);
                    return STRING_STATUS_OK;
                } else {
                    return "ERROR EN EL TIPO DE PETICION ";
                }

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

                boolean exitoAceptarRechazar = incidenciaService.aceptarORechazarIncidencia(idIncidencia, aceptar,
                        idEmpleado, prioridad, motivo);

                if (exitoAceptarRechazar) {
                    return STRING_STATUS_OK;
                } else {
                    return STRING_STATUS_ERROR;
                }

            case STRING_MANTENIMIENTO:
                //lista de empleados
                return personalMantenimientoService.listaOcupacionPersonal().toJSONString();

            case STRING_ESPACIO:
                return "Espacio";

            case STRING_AFORO:
                return "Aforo";

            case STRING_ESPACIOS:
                return "Espacios";

            case STRING_REGISTRO_MANTENIMIENTO:
                nombre = message.remove(0);
                email = message.remove(0);
                String apellidos = message.remove(0);
                contrasena = message.remove(0);
                exitoRegistro = personalMantenimientoService.registrarPersonalMantenimiento(email, nombre, apellidos, contrasena);
                if (exitoRegistro) {
                    return STRING_STATUS_OK;
                } else {
                    return STRING_STATUS_ERROR;
                }

            default:
                return "Error";
        }
    }

}