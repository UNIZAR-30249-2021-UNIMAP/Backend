package com.labis.appserver.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "idAdministrador")
public class Administrador extends Persona {

    public Administrador(){}

}
