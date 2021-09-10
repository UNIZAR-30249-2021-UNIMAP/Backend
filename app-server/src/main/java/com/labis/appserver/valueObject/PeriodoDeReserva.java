package com.labis.appserver.valueObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
public class PeriodoDeReserva {

    public LocalDate dia;

    public LocalTime horaInicio;

    public LocalTime horaFin;

    public PeriodoDeReserva() {
    }

    public PeriodoDeReserva(LocalDate diaReserva, LocalTime horaPrincipio, LocalTime horaFinal){
        this.dia = diaReserva;
        this.horaInicio = horaPrincipio;
        this.horaFin = horaFinal;
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
