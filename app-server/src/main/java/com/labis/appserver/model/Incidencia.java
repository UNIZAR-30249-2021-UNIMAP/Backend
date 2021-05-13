package com.labis.appserver.model;

import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.common.Priority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@OneToMany()
    @Column()
    private Long idEspacio;

    //@OneToMany()
    @Column()
    private Long idPersonalMantenimiento;

    @Column()
    private String estado;

    @Column()
    private String prioridad;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    @Column(name = "timestamp_asignado")
    private Timestamp asignadoTimeStamp;

    @Column(name = "timestamp_finalizado")
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
