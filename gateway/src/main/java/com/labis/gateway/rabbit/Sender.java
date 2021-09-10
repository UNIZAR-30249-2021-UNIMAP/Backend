package com.labis.gateway.rabbit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Login de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "-1 Usuario no existe, 1 usuario normal, 2 administrador, 3 mantenimiento")
    })
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

    @ApiOperation(value = "Registro de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "'" + STRING_STATUS_OK + "'" + " si la operación ha tenido éxito. " +
                    "'" + STRING_STATUS_ERROR + "'" + " si la operación no se ha podido realizar." )
    })
    @PostMapping(value = STRING_REGISTRO)
    public String registro(@RequestParam("nombreUsuario") String nombre,
     @RequestParam("email") String email, @RequestParam("contrasena") String contrasena) {
        ArrayList<String> infoUser = new ArrayList<String>();
        infoUser.add(STRING_REGISTRO);
        infoUser.add(email);
        infoUser.add(nombre);
        infoUser.add(contrasena);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", infoUser);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve un JSON con todas las incidencias con estado 'REPORTADA'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "[{\"descripcion\":,\"idEspacio\":," +
                    "\"reportadoTimeStamp\":,\"imagen\":,\"id\":}]")
    })
    @GetMapping(value = STRING_INCIDENCIA)
    public String getIncidencias() {
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Reportar una incidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STRING_STATUS_OK)
    })
    @PostMapping(value = STRING_INCIDENCIA_REPORTE)
    public String getIncidencias(@RequestParam("idEspacio") String idEspacio,
                                 @RequestParam("descripcion") String descripcion, @RequestParam("email") String email,
                                 @RequestParam("imagen") String imagen) {
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_REPORTE);
        incidencia.add(idEspacio); incidencia.add(descripcion); incidencia.add(email); incidencia.add(imagen);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Finaliza la incidencia indicada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STRING_STATUS_OK)
    })
    @PostMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String finalizarIncidencia(@RequestParam("idIncidencia") String idIncidencia) {
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO); incidencia.add("POST");
        incidencia.add(idIncidencia);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Asigna o rechaza una incidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "'" + STRING_STATUS_OK + "'" + " si la operación ha tenido éxito. " +
                    "'" + STRING_STATUS_ERROR + "'" + " si la operación no se ha podido realizar." )
    })
    @PostMapping(value = STRING_INCIDENCIA_ADMIN)
    public String asignarRechazarIncidencia(@RequestParam("idIncidencia") String idIncidencia,
     @RequestParam("aceptar") String aceptar, @RequestParam("idEmpleado") String idEmpleado,
     @RequestParam("prioridad") String prioridad, @RequestParam("motivo") String motivo) {
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_ADMIN);
        incidencia.add(idIncidencia); incidencia.add(aceptar); incidencia.add(idEmpleado); incidencia.add(prioridad);
        incidencia.add(motivo);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve las incidencias asignadas a un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"tareasNormales\":[{\"id\":,\"estado\":,\"prioridad\":}]," +
                    "\"tareasUrgentes\":[{\"id\":,\"estado\":,\"prioridad\":}]}")
    })
    @GetMapping(value = STRING_INCIDENCIA_MANTENIMIENTO)
    public String getIncidenciasMantenimiento(@RequestParam("ID") String ID) {
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add(STRING_INCIDENCIA_MANTENIMIENTO); incidencia.add("GET");
        incidencia.add(ID);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve una lista de empleados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "[{\"tareasNormales\":,\"id\":,\"tareasUrgentes\":,\"nombre\":}]")
    })
    @GetMapping(value = STRING_MANTENIMIENTO)
    public String getEmpleados() {
        ArrayList<String> mantenimiento = new ArrayList<String>();
        mantenimiento.add(STRING_MANTENIMIENTO);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", mantenimiento);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve los espacios que cumplen con los parámetros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"tareasNormales\":[{\"id\":,\"estado\":,\"prioridad\":}]," +
                    "\"tareasUrgentes\":[{\"id\":,\"estado\":,\"prioridad\":}]}")
    })
    @GetMapping(value = STRING_ESPACIOS)
    public String getEspaciosParametrizados(@RequestParam("proyector") String proyector,
     @RequestParam("edificio") String edificio, @RequestParam("planta") String planta,
     @RequestParam("tipoSala") String tipoSala,@RequestParam("fechaInicio") String fechaInicio, 
     @RequestParam("fechaFin") String fechaFin, @RequestParam("horaInicio") String horaInicio, 
     @RequestParam("horaFin") String horaFin) {
        ArrayList<String> espacio = new ArrayList<String>();
        espacio.add(STRING_ESPACIOS);
        espacio.add(proyector); espacio.add(edificio); espacio.add(planta);
        espacio.add(tipoSala); espacio.add(fechaInicio); espacio.add(fechaFin);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", espacio);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve las incidencias asignadas a un empleado")
    @ApiResponse(code = 200, message = "OK")
    @PostMapping(value = STRING_ESPACIO)
    public String reservaEspacio( @RequestParam("edificio") String edificio, @RequestParam("idSala") String idSala, @RequestParam("nombreUsuario") String nombreUsuario,
     @RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin) {
        ArrayList<String> reserva = new ArrayList<String>();
        reserva.add(STRING_ESPACIO);
        reserva.add(edificio);
        reserva.add(idSala); reserva.add(nombreUsuario); reserva.add(fechaInicio); reserva.add(fechaFin);
        
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", reserva);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Devuelve la información de un espacio dado su edificio e identificador de espacio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"tareasNormales\":[{\"id\":,\"estado\":,\"prioridad\":}]," +
                    "\"tareasUrgentes\":[{\"id\":,\"estado\":,\"prioridad\":}]}")
    })
    @GetMapping(value = STRING_ESPACIO)
    public String getInfoEspacio(@RequestParam("edificio") String edificio, @RequestParam("idSala") String idSala) {
        ArrayList<String> espacio = new ArrayList<String>();
        espacio.add(STRING_ESPACIO); espacio.add(idSala); //TODO revisar

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", espacio);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = STRING_AFORO)
    public String setAforo() {
        ArrayList<String> aforo = new ArrayList<String>();
        aforo.add(STRING_AFORO);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", aforo);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @ApiOperation(value = "Registra a un personal de mantenimiento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "'" + STRING_STATUS_OK + "'" + " si la operación ha tenido éxito. " +
                    "'" + STRING_STATUS_ERROR + "'" + " si la operación no se ha podido realizar." )
    })
    @PostMapping(value = STRING_REGISTRO_MANTENIMIENTO)
    public String registroPersonalMantenimiento(@RequestParam("nombreUsuario") String nombre,
                                                @RequestParam("apellidos") String apellidos,
                                                @RequestParam("email") String email,
                                                @RequestParam("contrasena") String contrasena) {
        ArrayList<String> registro = new ArrayList<String>();
        registro.add(STRING_REGISTRO_MANTENIMIENTO);
        registro.add(nombre); registro.add(email); registro.add(apellidos); registro.add(contrasena);

        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", registro);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }
}
