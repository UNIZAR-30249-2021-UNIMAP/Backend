package com.labis.appserver.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String nombre;
    private String apellidos;

    public Persona() {}

    public Persona(String email, String nombre, String apellidos) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    //Mediante registro, es posible que no se soliciten apellidos
    public Persona(String email, String nombre) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = "";
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
}
