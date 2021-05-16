package com.labis.appserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reserva {

    @Id
    @GeneratedValue()
    private Long id;

    //@OneToOne
    private Long idEspacio;

    //@OneToOne
    private Long idUsuarioQueReserva;


    public Reserva(){}
}
