package com.labis.gateway.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import static com.labis.gateway.common.Constantes.*;

@RestController
public class Sender {

    @Autowired
    private RabbitTemplate template;

    static final String directExchangeName = "tut.rpc";

    @PostMapping(value = STRING_LOGIN)
    public String login(@RequestParam("email") String email, @RequestParam("contrasena") String contrasena) {
        System.out.println("Sending message...");
        ArrayList<String> infoUser = new ArrayList<String>();
        infoUser.add(STRING_LOGIN);
        infoUser.add(email);
        infoUser.add(contrasena);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", infoUser);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_REGISTRO)
    public String registro(@RequestParam("nombreUsuario") String nombre,
     @RequestParam("email") String email, @RequestParam("contrasena") String contrasena) {
        System.out.println("Sending message...");
        ArrayList<String> infoUser = new ArrayList<String>();
        infoUser.add(STRING_REGISTRO);
        infoUser.add(nombre);
        infoUser.add(email);
        infoUser.add(contrasena);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", infoUser);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_INCIDENCIA)
    public String getIncidencias() {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String finalizarIncidencia(@RequestParam("idIncidencia") String idIncidencia) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO);
        incidencia.add(idIncidencia);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String getIncidenciasMantenimiento() {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_INCIDENCIA_ADMIN)
    public String asignarRechazarIncidencia(@RequestParam("idIncidencia") String idIncidencia,
     @RequestParam("aceptar") String aceptar, @RequestParam("idEmpleado") String idEmpleado,
     @RequestParam("prioridad") String prioridad) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_ADMIN);
        incidencia.add(idIncidencia); incidencia.add(aceptar); incidencia.add(idEmpleado); incidencia.add(prioridad);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_MANTENIMIENTO)
    public String getEmpleados() {
        System.out.println("Sending message...");
        ArrayList<String> mantenimiento = new ArrayList<String>();
        mantenimiento.add(STRING_MANTENIMIENTO);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", mantenimiento);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_ESPACIOS)
    public String getEspaciosParametrizados(@RequestParam("aforoMinimo") String aforoMinimo, @RequestParam("proyector") String proyector,
     @RequestParam("edificio") String edificio, @RequestParam("planta") String planta,
     @RequestParam("tipoSala") String tipoSala,@RequestParam("fechaInicio") String fechaInicio, 
     @RequestParam("fechaFin") String fechaFin, @RequestParam("horaInicio") String horaInicio, 
     @RequestParam("horaFin") String horaFin) {
        System.out.println("Sending message...");
        ArrayList<String> espacio = new ArrayList<String>();
        espacio.add(STRING_ESPACIOS);
        espacio.add(aforoMinimo); espacio.add(proyector); espacio.add(edificio); espacio.add(planta);
        espacio.add(tipoSala); espacio.add(fechaInicio); espacio.add(fechaFin); espacio.add(horaInicio);
        espacio.add(horaFin);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", espacio);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_ESPACIO)
    public String reservaEspacio( @RequestParam("idSala") String idSala, @RequestParam("nombreUsuario") String nombreUsuario,
     @RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin,
     @RequestParam("horaInicio") String horaInicio, @RequestParam("horaFin") String horaFin,
     @RequestParam("semanal") String semanal, @RequestParam("email") String email,
     @RequestParam("telefono") String telefono) {
        System.out.println("Sending message...");
        ArrayList<String> reserva = new ArrayList<String>();
        reserva.add(STRING_ESPACIO);
        reserva.add(idSala); reserva.add(nombreUsuario); reserva.add(fechaInicio); reserva.add(fechaFin); 
        reserva.add(horaInicio); reserva.add(semanal); reserva.add(email); reserva.add(telefono);
        
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", reserva);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_ESPACIO)
    public String getInfoEspacio(@RequestParam("idSala") String idSala) {
        System.out.println("Sending message...");
        ArrayList<String> espacio = new ArrayList<String>();
        espacio.add(STRING_ESPACIO); espacio.add(idSala); //TODO revisar

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", espacio);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_AFORO)
    public String setAforo() {
        System.out.println("Sending message...");
        ArrayList<String> aforo = new ArrayList<String>();
        aforo.add(STRING_AFORO);
        
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", aforo);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }
}
