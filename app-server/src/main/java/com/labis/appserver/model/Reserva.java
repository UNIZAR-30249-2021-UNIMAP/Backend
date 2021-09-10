package com.labis.appserver.model;

import com.labis.appserver.valueObject.PeriodoDeReserva;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reserva {

    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    private Espacio espacio;

    @ManyToOne
    private Persona reservadoPor;

    @Embedded
    private PeriodoDeReserva periodoDeReserva;


    public Reserva(){}

    public Reserva(Espacio espacioReservado, Persona quienReserva, PeriodoDeReserva cuando) {
        this.espacio = espacioReservado;
        this.reservadoPor = quienReserva;
        this.periodoDeReserva = cuando;
    }

    public LocalDate getDiaReserva() {
        return this.periodoDeReserva.dia;
    }

    public LocalTime getHoraInicio() {
        return this.periodoDeReserva.horaInicio;
    }

    public LocalTime getHoraFin() {
        return this.getHoraFin();
    }
}
