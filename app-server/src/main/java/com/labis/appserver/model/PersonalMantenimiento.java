package com.labis.appserver.model;

import com.labis.appserver.common.Priority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;

@Entity
@PrimaryKeyJoinColumn(name = "idPersonalMantenimiento")
public class PersonalMantenimiento extends Persona {

    @OneToMany(mappedBy = "incidencia_idPersonalMantenimiento")
    ArrayList<Incidencia> tareasNormales;

    @OneToMany(mappedBy = "incidencia_idPersonalMantenimiento")
    ArrayList<Incidencia> tareasUrgentes;

    Integer maxNumTareasNormales;

    Integer maxNumTareasUrgentes;

    public PersonalMantenimiento() {
        this.tareasNormales = new ArrayList<Incidencia>();
        this.tareasUrgentes = new ArrayList<Incidencia>();
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
