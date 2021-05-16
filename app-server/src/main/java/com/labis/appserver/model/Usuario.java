package com.labis.appserver.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Usuario extends Persona {

    Integer NIP;

    Integer NumeroTelefono;

    public Usuario(){}

    public Usuario(Integer inNIP, Integer inTelefono) {
        this.NIP = inNIP;
        this.NumeroTelefono = inTelefono;
    }
}

