package com.labis.appserver.model;

import com.labis.appserver.common.Constantes;
import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.valueObject.IncidenciaObjetoValor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PersonalMantenimiento personalMantenimientoNormal;

    @ManyToOne
    private PersonalMantenimiento personalMantenimientoUrgente;

    //Variables del reporte
    private Long idEspacio;

    //Variables del estado de la incidencia
    private String estado;
    private String prioridad;
    private String motivoRechazo;
    private Timestamp asignadoTimeStamp;
    private Timestamp finalizadoTimeStamp;

    private IncidenciaObjetoValor incidenciaObjetoValor;

    public Incidencia() {}

    public Incidencia(Long idEspacio, String descripcion, String email, String imagen){
        incidenciaObjetoValor = new IncidenciaObjetoValor(descripcion, email, imagen, Timestamp.from(Instant.now()));
        this.estado = IssueStatus.REPORTADO.toString();
        this.idEspacio = idEspacio;
    }

    public void rechazar(String motivo) {
        this.estado = IssueStatus.RECHAZADA.toString();
        this.finalizadoTimeStamp = Timestamp.from(Instant.now());
        this.motivoRechazo = motivo;
    }

    public void asignarPrioridadNormal(PersonalMantenimiento personalMantenimiento) {
        this.personalMantenimientoNormal = personalMantenimiento;
        this.estado = IssueStatus.PENDIENTE.toString();
        this.prioridad = Constantes.STRING_PRIORIDAD_NORMAL;
        this.asignadoTimeStamp = Timestamp.from(Instant.now());
    }

    public void asignarPrioridadUrgente(PersonalMantenimiento personalMantenimiento) {
        this.personalMantenimientoUrgente = personalMantenimiento;
        this.estado = IssueStatus.PENDIENTE.toString();
        this.prioridad = Constantes.STRING_PRIORIDAD_URGENTE;
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

    public String getEmail() { return incidenciaObjetoValor.email; }

    public String getDescripcion() { return incidenciaObjetoValor.descripcion; }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", personalMantenimientoNormal=" + personalMantenimientoNormal +
                ", personalMantenimientoUrgente=" + personalMantenimientoUrgente +
                ", idEspacio=" + idEspacio +
                ", descripcion='" + incidenciaObjetoValor.descripcion + '\'' +
                ", email='" + incidenciaObjetoValor.email + '\'' +
                ", imagen='" + incidenciaObjetoValor.imagen + '\'' +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", reportadoTimeStamp=" + incidenciaObjetoValor.reportadoTimeStamp +
                ", asignadoTimeStamp=" + asignadoTimeStamp +
                ", finalizadoTimeStamp=" + finalizadoTimeStamp +
                '}';
    }
}
