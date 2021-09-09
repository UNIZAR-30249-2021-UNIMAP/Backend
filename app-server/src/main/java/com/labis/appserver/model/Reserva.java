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
    private Usuario reservadoPor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="periodo_id", referencedColumnName = "id")
    private PeriodoDeReserva periodoDeReserva;


    public Reserva(){}

    public Reserva(Espacio espacioReservado, Usuario quienReserva, PeriodoDeReserva cuando) {
        this.espacio = espacioReservado;
        this.reservadoPor = quienReserva;
        this.periodoDeReserva = cuando;
    }

    public LocalDate getDiaReserva() {
        return this.periodoDeReserva.getDia();
    }

    public LocalTime getHoraInicio() {
        return this.periodoDeReserva.getHoraInicio();
    }

    public LocalTime getHoraFin() {
        return this.getHoraFin();
    }
}
