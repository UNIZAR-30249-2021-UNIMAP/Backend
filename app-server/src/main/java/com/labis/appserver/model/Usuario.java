package com.labis.appserver.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Usuario extends Persona {

    String contrasena;
    int tipo_usuario;

    public Usuario() {}

    public Usuario(String email, String nombre, String contrasena) {
        super(email, nombre);
        this.contrasena = contrasena;
    }

    public int getTipo_usuario() {
        return tipo_usuario;
    }
}

