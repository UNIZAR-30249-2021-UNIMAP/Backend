package com.labis.gateway.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import static com.labis.gateway.common.Constantes.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class Sender {

    @Autowired
    private RabbitTemplate template;

    static final String directExchangeName = "tut.rpc";

    @PostMapping(value = STRING_LOGIN)
    public String login(@RequestParam(value="email") String email, @RequestParam(value="contrasena") String contrasena) {
        System.out.println("Values: " +email +" " +contrasena);
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
        infoUser.add(email);
        infoUser.add(nombre);
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

    @PostMapping(value = STRING_INCIDENCIA_REPORTE)
    public String getIncidencias(@RequestParam("idEspacio") String idEspacio,
                                 @RequestParam("descripcion") String descripcion, @RequestParam("email") String email,
                                 @RequestParam("imagen") String imagen) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_REPORTE);
        incidencia.add(idEspacio); incidencia.add(descripcion); incidencia.add(email); incidencia.add(imagen);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String finalizarIncidencia(@RequestParam("idIncidencia") String idIncidencia) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO); incidencia.add("POST");
        incidencia.add(idIncidencia);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_INCIDENCIA_ADMIN)
    public String asignarRechazarIncidencia(@RequestParam("idIncidencia") String idIncidencia,
     @RequestParam("aceptar") String aceptar, @RequestParam("idEmpleado") String idEmpleado,
     @RequestParam("prioridad") String prioridad, @RequestParam("motivo") String motivo) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_ADMIN);
        incidencia.add(idIncidencia); incidencia.add(aceptar); incidencia.add(idEmpleado); incidencia.add(prioridad);
        incidencia.add(motivo);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @GetMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String getIncidenciasMantenimiento(@RequestParam("ID") String ID) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO); incidencia.add("GET");
        incidencia.add(ID);
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
     @RequestParam("fechaFin") String fechaFin) {
        System.out.println("Sending message...");
        ArrayList<String> espacio = new ArrayList<String>();
        espacio.add(STRING_ESPACIOS);
        espacio.add(aforoMinimo); espacio.add(proyector); espacio.add(edificio); espacio.add(planta);
        espacio.add(tipoSala); espacio.add(fechaInicio); espacio.add(fechaFin);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", espacio);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_ESPACIO)
    public String reservaEspacio( @RequestParam("idSala") String idSala, @RequestParam("nombreUsuario") String nombreUsuario,
     @RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin,
     @RequestParam("semanal") String semanal) {
        System.out.println("Sending message...");
        ArrayList<String> reserva = new ArrayList<String>();
        reserva.add(STRING_ESPACIO);
        reserva.add(idSala); reserva.add(nombreUsuario); reserva.add(fechaInicio); reserva.add(fechaFin);
        reserva.add(semanal);
        
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
