package com.labis.appserver.valueObject;

import com.labis.appserver.model.Reserva;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class PeriodoDeReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dia;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    @OneToOne(mappedBy = "periodoDeReserva")
    private Reserva reserva;

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

    public LocalTime getHoraFin() {
        return this.horaFin;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id:" + id +
                ", dia:" + this.dia.toString() +
                ", horaInicio:" + this.horaInicio.toString() +
                ", horaFin" + this.horaFin.toString() +
                '}';
    }
}
