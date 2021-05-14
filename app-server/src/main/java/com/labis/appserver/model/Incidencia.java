package com.labis.appserver.model;

import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.common.Priority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToMany()
    private Long idEspacio;

    //@OneToMany()
    private Long idPersonalMantenimiento;

    private String estado;

    private String prioridad;

    private String motivoRechazo;

    private Timestamp asignadoTimeStamp;

    private Timestamp finalizadoTimeStamp;

    public Incidencia() {

    }

    public Incidencia(Long idEspacio){
        this.estado = IssueStatus.REPORTADO.toString();
        this.idEspacio = idEspacio;
    }

    public void setPrioridadNormal(){
        this.prioridad = Priority.NORMAL.toString();
    }

    public void setPrioridadUrgente(){
        this.prioridad = Priority.URGENTE.toString();
    }

    public void rechazar(String motivo) {
        this.estado = IssueStatus.RECHAZADA.toString();
        this.motivoRechazo = motivo;
    }

    public void finalizar() {
        this.estado = IssueStatus.FINALIZADA.toString();
        this.finalizadoTimeStamp = Timestamp.from(Instant.now());
    }

    public void asignar(Long idMantenimiento) {
        this.estado = IssueStatus.PENDIENTE.toString();
        this.idPersonalMantenimiento = idMantenimiento;
        this.asignadoTimeStamp = Timestamp.from(Instant.now());

    }

    public String getEstado() {
        return this.estado;
    }

    public String getPrioridad() {
        return this.prioridad;
    }

    public String getMotivoRechazo(){
        return this.motivoRechazo;
    }

}
