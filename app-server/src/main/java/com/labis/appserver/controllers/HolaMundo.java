package com.labis.appserver.controllers;

import java.util.ArrayList;

public class HolaMundo {

    public String saludo(ArrayList<String> who) {
        return "Hola, " + who.get(0);
    }

    public String incidencia(ArrayList<String> who) {
        return "Hola, " + who;
    }
}
