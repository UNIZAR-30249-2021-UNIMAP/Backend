package com.labis.appserver.model;

import com.labis.appserver.common.Priority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "personalMantenimiento_id")
public class PersonalMantenimiento extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idPersonalMantenimiento;

    @OneToMany(mappedBy = "idPersonalMantenimiento")
    private Set<Incidencia> tareasNormales;

    @OneToMany(mappedBy = "idPersonalMantenimiento")
    private Set<Incidencia> tareasUrgentes;

    Integer maxNumTareasNormales;

    Integer maxNumTareasUrgentes;

    public PersonalMantenimiento() {
        this.tareasNormales = new HashSet<Incidencia>();
        this.tareasUrgentes = new HashSet<Incidencia>();
        this.maxNumTareasNormales = 5;
        this.maxNumTareasUrgentes = 1;
    }

    public boolean anyadirTareaNormal(Incidencia nuevaIncidencia) {
        if (nuevaIncidencia.getEstado().equals(Priority.NORMAL.toString())
                && this.tareasNormales.size() < this.maxNumTareasNormales) {
            this.tareasNormales.add(nuevaIncidencia);
            return true;
        } else {
            return false;
        }
    }

    public boolean anyadirTareaUrgente(Incidencia nuevaIncidencia){
        if (nuevaIncidencia.getEstado().equals(Priority.URGENTE.toString())
                && this.tareasNormales.size() < this.maxNumTareasUrgentes) {
            this.tareasNormales.add(nuevaIncidencia);
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
}
