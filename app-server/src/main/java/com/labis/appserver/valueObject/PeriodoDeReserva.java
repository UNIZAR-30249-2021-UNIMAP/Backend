package com.labis.appserver.valueObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
public class PeriodoDeReserva {

    private LocalDate dia;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    public PeriodoDeReserva() {
    }

    public PeriodoDeReserva(LocalDate diaReserva, LocalTime horaPrincipio, LocalTime horaFinal){
        this.dia = diaReserva;
        this.horaInicio = horaPrincipio;
        this.horaFin = horaFinal;
    }

    public LocalDate getDia() {
        return this.dia;
    }

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                ", dia:" + this.dia.toString() +
                ", horaInicio:" + this.horaInicio.toString() +
                ", horaFin" + this.horaFin.toString() +
                '}';
    }
}
