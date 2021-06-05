package com.labis.appserver.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String correo;
    private String nombre;
    private String apellidos;

    public Persona() {}

    public Persona(String correo, String nombre, String apellidos) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
}
