package com.labis.appserver.model;

import com.labis.appserver.valueObject.PeriodoDeReserva;
import org.apache.tomcat.jni.Local;

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

    public Reserva(Espacio espacioReservado, Persona quienReserva, LocalDate dia, LocalTime horaInicio, LocalTime horaFin) {
        this.espacio = espacioReservado;
        this.reservadoPor = quienReserva;
        this.periodoDeReserva = new PeriodoDeReserva(dia, horaInicio, horaFin);
    }

    public LocalDate getDiaReserva() {
        return periodoDeReserva.dia;
    }

    public LocalTime getHoraInicio() {
        return periodoDeReserva.horaInicio;
    }
    public LocalTime getHoraFin() { return periodoDeReserva.horaFin; }

    public Long getId() { return  id; }

}
