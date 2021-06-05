package com.labis.appserver.valueObject;

import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.common.Priority;
import com.labis.appserver.model.PersonalMantenimiento;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PersonalMantenimiento personalMantenimiento;

    private Long idEspacio;
    private String estado;
    private String prioridad;
    private String motivoRechazo;
    private Timestamp reportadoTimeStamp;
    private Timestamp asignadoTimeStamp;
    private Timestamp finalizadoTimeStamp;

    public Incidencia() {
        this.estado = IssueStatus.REPORTADO.toString();
        this.reportadoTimeStamp = Timestamp.from(Instant.now());
    }

    public Incidencia(Long idEspacio){
        this.estado = IssueStatus.REPORTADO.toString();
        this.reportadoTimeStamp = Timestamp.from(Instant.now());
        this.idEspacio = idEspacio;
    }

    public void rechazar(String motivo) {
        this.estado = IssueStatus.RECHAZADA.toString();
        this.motivoRechazo = motivo;
    }

    public void asignarPrioridadNormal(PersonalMantenimiento personalMantenimiento) {
        this.personalMantenimiento = personalMantenimiento;
        this.estado = IssueStatus.PENDIENTE.toString();
        this.prioridad = Priority.NORMAL.toString();
        this.asignadoTimeStamp = Timestamp.from(Instant.now());
    }

    public void asignarPrioridadUrgente(PersonalMantenimiento personalMantenimiento) {
        this.personalMantenimiento = personalMantenimiento;
        this.estado = IssueStatus.PENDIENTE.toString();
        this.prioridad = Priority.URGENTE.toString();
        this.asignadoTimeStamp = Timestamp.from(Instant.now());
    }

    public void finalizar() {
        this.estado = IssueStatus.FINALIZADA.toString();
        this.finalizadoTimeStamp = Timestamp.from(Instant.now());
    }


    public Long getId() {
        return id;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getPrioridad() {
        return this.prioridad;
    }

    public String getMotivoRechazo() {
        return this.motivoRechazo;
    }

    public Timestamp getReportadoTimeStamp() {
        return reportadoTimeStamp;
    }

    public Timestamp getAsignadoTimeStamp() {
        return asignadoTimeStamp;
    }

    public Timestamp getFinalizadoTimeStamp() {
        return finalizadoTimeStamp;
    }

    public PersonalMantenimiento getPersonalMantenimiento() {
        return personalMantenimiento;
    }
}
