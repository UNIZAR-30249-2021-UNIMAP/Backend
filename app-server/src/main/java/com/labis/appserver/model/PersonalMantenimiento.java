package com.labis.appserver.model;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.valueObject.Incidencia;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PersonalMantenimiento extends Persona {
    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    @OneToMany(mappedBy = "personalMantenimiento", fetch = FetchType.EAGER)
    private Set<Incidencia> tareasNormales;

    @OneToMany(mappedBy = "personalMantenimiento", fetch = FetchType.EAGER)
    private Set<Incidencia> tareasUrgentes;

    Integer maxNumTareasNormales;
    Integer maxNumTareasUrgentes;

    public PersonalMantenimiento() {}

    public PersonalMantenimiento(String correo, String nombre, String apellidos) {
        super(correo, nombre, apellidos);
        this.tareasNormales = new HashSet<Incidencia>();
        this.tareasUrgentes = new HashSet<Incidencia>();
        this.maxNumTareasNormales = 5;
        this.maxNumTareasUrgentes = 1;
    }

    public boolean anyadirIncidenciaNormal(Incidencia incidencia) {
        log.info("tamaño tareas normales: " + this.tareasNormales.size());

        if (incidencia.getEstado().equals(IssueStatus.REPORTADO.toString())
                && this.tareasNormales.size() < this.maxNumTareasNormales) {

            this.tareasNormales.add(incidencia);
            incidencia.asignarPrioridadNormal(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean anyadirIncidenciaUrgente(Incidencia incidencia){
        log.info("tamaño tareas urgentes: " + this.tareasUrgentes.size());
        if (incidencia.getEstado().equals(IssueStatus.REPORTADO.toString())
                && this.tareasUrgentes.size() < this.maxNumTareasUrgentes) {

            this.tareasUrgentes.add(incidencia);
            incidencia.asignarPrioridadUrgente(this);
            return true;
        } else  {
            return false;
        }
    }

    public boolean finalizarIncidencia(Incidencia incidencia) {
        incidencia.finalizar();
        if (tareasNormales.contains(incidencia)) {
            return tareasNormales.remove(incidencia);
        } else {
            return tareasUrgentes.remove(incidencia);
        }
    }

    public void cambiarMaxTareasNormales(Integer nuevoMaxNormales){
        this.maxNumTareasNormales = nuevoMaxNormales;
    }

    public void cambiarMaxTareasUrgentes(Integer nuevoMaxUrgentes) {
        this.maxNumTareasUrgentes = nuevoMaxUrgentes;
    }
}
