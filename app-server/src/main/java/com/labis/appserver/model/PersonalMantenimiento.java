package com.labis.appserver.model;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.common.IssueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.labis.appserver.common.Constantes.TIPO_MANTENIMIENTO;

@Entity
public class PersonalMantenimiento extends Persona {
    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    @OneToMany(mappedBy = "personalMantenimientoNormal", fetch = FetchType.EAGER)
    private Set<Incidencia> tareasNormales;

    @OneToMany(mappedBy = "personalMantenimientoUrgente", fetch = FetchType.EAGER)
    private Set<Incidencia> tareasUrgentes;

    Integer maxNumTareasNormales;
    Integer maxNumTareasUrgentes;

    public PersonalMantenimiento() {}

    public PersonalMantenimiento(String email, String nombre, String apellidos, String contrasena) {
        super(email, nombre, apellidos, contrasena, TIPO_MANTENIMIENTO);
        this.tareasNormales = new HashSet<Incidencia>();
        this.tareasUrgentes = new HashSet<Incidencia>();
        this.maxNumTareasNormales = 5;
        this.maxNumTareasUrgentes = 1;
    }

    //Asigna la incidencia con prioridad NORMAL
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

    //Asigna la incidencia con prioridad URGENTE
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

    public void cambiarMaxTareasNormales(Integer nuevoMaxNormales){
        this.maxNumTareasNormales = nuevoMaxNormales;
    }

    public void cambiarMaxTareasUrgentes(Integer nuevoMaxUrgentes) {
        this.maxNumTareasUrgentes = nuevoMaxUrgentes;
    }

    public Set<Incidencia> getTareasNormales() {
        return tareasNormales;
    }

    public Set<Incidencia> getTareasUrgentes() {
        return tareasUrgentes;
    }

    public int getNumTareasNormales() { return tareasNormales.size(); }

    public int getNumTareasUrgentes() { return tareasUrgentes.size(); }
}
