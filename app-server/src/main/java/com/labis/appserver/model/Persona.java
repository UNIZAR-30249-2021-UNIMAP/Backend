package com.labis.appserver.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue()
    private Long id;

    private String correo;

    private String nombre;

    private String apellidos;

    public Persona(){

    }

    public Persona(String inCorreo, String inNombre, String inApellidos) {
        this.correo = inCorreo;
        this.nombre = inNombre;
        this.apellidos = inApellidos;
    }
}
