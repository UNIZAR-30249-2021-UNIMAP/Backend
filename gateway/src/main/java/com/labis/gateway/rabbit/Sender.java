package com.labis.gateway.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;


@RestController
@RequestMapping(value="/api/v1/")
public class Sender {

    @Autowired
    private RabbitTemplate template;

    static final String directExchangeName = "tut.rpc";

    @GetMapping(value = "user")
    public String publishUserDetails(@RequestParam("nombre") String nombre) {
        System.out.println("Sending message...");
        ArrayList<String> users = new ArrayList<String>();
        users.add("users");
        users.add(nombre);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", users);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }

    @PostMapping(value = "incidencia")
    public String publishUserDetails(@RequestParam("descripcion") String descripcion,
                                     @RequestParam("email") String email,
                                     @RequestParam("IDEspacio") String IDEspacio) {
        System.out.println("Sending message...");
        ArrayList<String> incidencia = new ArrayList<String>();
        incidencia.add("incidencias");
        incidencia.add(descripcion); incidencia.add(email); incidencia.add(IDEspacio);
        String response = (String) template.convertSendAndReceive(directExchangeName, "rpc", incidencia);
        System.out.println("Received in 'gateway/Sender' <" + response + ">");
        return response;
    }
}
