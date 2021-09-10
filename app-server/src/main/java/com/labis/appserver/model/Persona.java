package com.labis.appserver.model;

import javax.persistence.*;

import static com.labis.appserver.common.Constantes.TIPO_NORMAL;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String nombre;
    private String apellidos;
    private String contrasena;
    private int tipoUsuario;

    public Persona() {}

    public Persona(String email, String nombre, String contrasena) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = "";
        this.contrasena = contrasena;
        this.tipoUsuario = TIPO_NORMAL;
    }

    public Persona(String email, String nombre, String apellidos, String contrasena, int tipoUsuario) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
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

    public int getTipoUsuario() { return tipoUsuario; }
}
