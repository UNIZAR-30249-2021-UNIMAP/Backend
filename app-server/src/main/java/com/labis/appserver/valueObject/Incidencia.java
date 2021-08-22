package com.labis.appserver.valueObject;

import com.labis.appserver.common.Constantes;
import com.labis.appserver.common.IssueStatus;
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
    private PersonalMantenimiento personalMantenimientoNormal;

    @ManyToOne
    private PersonalMantenimiento personalMantenimientoUrgente;

    //Variables del reporte
    private Long idEspacio;
    private String descripcion;
    private String email;
    private String imagen;

    //Variables del estado de la incidencia
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

    public Incidencia(Long idEspacio, String descripcion, String email, String imagen){
        this.estado = IssueStatus.REPORTADO.toString();
        this.reportadoTimeStamp = Timestamp.from(Instant.now());
        this.idEspacio = idEspacio;
        this.descripcion = descripcion;
        this.email = email;
        this.imagen = imagen;
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

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", personalMantenimientoNormal=" + personalMantenimientoNormal +
                ", personalMantenimientoUrgente=" + personalMantenimientoUrgente +
                ", idEspacio=" + idEspacio +
                ", descripcion='" + descripcion + '\'' +
                ", email='" + email + '\'' +
                ", imagen='" + imagen + '\'' +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", reportadoTimeStamp=" + reportadoTimeStamp +
                ", asignadoTimeStamp=" + asignadoTimeStamp +
                ", finalizadoTimeStamp=" + finalizadoTimeStamp +
                '}';
    }
}
