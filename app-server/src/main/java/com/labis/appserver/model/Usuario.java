package com.labis.appserver.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Usuario extends Persona {

    String contrasena;

    @OneToMany(mappedBy = "reservadoPor", fetch = FetchType.EAGER)
    private Set<Reserva> reservas;

    public Usuario() {}

    public Usuario(String email, String nombre, String contrasena) {
        super(email, nombre);
        this.contrasena = contrasena;
    }
}

