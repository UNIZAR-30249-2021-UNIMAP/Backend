package com.labis.appserver.model;

import com.labis.appserver.common.Edificio;
import com.labis.appserver.common.TipoDeEspacio;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer ocupacion;  // Ocupación actual
    private Integer aforo;      // Capacidad máxima del Espacio
    private Integer aforoActual;// Capacidad que puede usarse actualmente
    private TipoDeEspacio tipoDeEspacio;
    private Edificio edificio;
    private Integer planta;

    private Integer ordenadoresPotenciales;
    private Integer ordenadoresActuales;

    private Integer sillasPotenciales;
    private Integer sillasActuales;

    private Integer mesasPotenciales;
    private Integer mesasActuales;

    private Integer proyectoresPotenciales;
    private Integer proyectoresActuales;

    @OneToMany(mappedBy = "espacio", fetch = FetchType.EAGER)
    private Set<Reserva> reservas;


    public Espacio() {}

    public Espacio(Integer aforoInicial, TipoDeEspacio tipoEspacio, Edificio edificio, Integer planta,
                   Integer ordenadores, Integer sillas, Integer mesas, Integer proyectores)  {
        this.ocupacion = 0;
        this.aforo = aforoInicial;
        this.aforoActual = aforoInicial;
        this.tipoDeEspacio = tipoEspacio;
        this.edificio = edificio;
        this.planta = planta;
        this.ordenadoresPotenciales = ordenadores;
        this.ordenadoresActuales = ordenadores;
        this.sillasPotenciales = sillas;
        this.sillasActuales = sillas;
        this.mesasPotenciales = mesas;
        this.mesasActuales = mesas;
        this.proyectoresPotenciales = proyectores;
        this.proyectoresActuales = proyectores;
        this.reservas = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Integer getAforo() {
        return aforo;
    }

    public Integer getAforoActual() {
        return aforoActual;
    }

    public TipoDeEspacio getTipoDeEspacio() {
        return tipoDeEspacio;
    }

    public Integer getPlanta() {
        return planta;
    }

    public Integer getOrdenadoresPotenciales() {
        return ordenadoresPotenciales;
    }

    public Integer getOrdenadoresActuales() {
        return ordenadoresActuales;
    }

    public Integer getSillasPotenciales() {
        return sillasPotenciales;
    }

    public Integer getSillasActuales() {
        return sillasActuales;
    }

    public Integer getMesasPotenciales() {
        return mesasPotenciales;
    }

    public Integer getMesasActuales() {
        return mesasActuales;
    }

    public Integer getProyectoresPotenciales() {
        return proyectoresPotenciales;
    }

    public Integer getProyectoresActuales() {
        return proyectoresActuales;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public Integer getOcupacion() {
        return ocupacion;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public boolean entrar() {
        if ( this.ocupacion < this.aforoActual ) {
            this.ocupacion = this.ocupacion + 1;
            return true;
        }
        return false;
    }

    public void salir() {
        if ( this.ocupacion > 0 ) {
            this.ocupacion = this.ocupacion - 1;
        }
    }

    public void cambiarAforoActual(Integer nuevoAforo) {
        this.aforoActual = nuevoAforo;
    }

    public void ocuparOrdenador() {
        this.ordenadoresActuales = this.ordenadoresActuales - 1;
    }

    public void liberarOrdenador() {
        this.ordenadoresActuales = this.ordenadoresActuales + 1;
    }

    public void ocuparSilla() {
        this.sillasActuales = this.sillasActuales - 1;
    }

    public void liberarSilla() {
        this.sillasActuales = this.sillasActuales + 1;
    }

    public void ocuparMesa() {
        this.mesasActuales = this.mesasActuales - 1;
    }

    public void liberarMesa() {
        this.mesasActuales = this.mesasActuales + 1;
    }

    public void ocuparProyector() {
        this.proyectoresActuales = this.proyectoresActuales - 1;
    }

    public void liberarProyector() {
        this.proyectoresActuales = this.proyectoresActuales + 1;
    }

    public void anyadirReserva(Reserva r) {
        this.reservas.add(r);
    }

    public void eliminarReserva(Reserva r){
        this.reservas.remove(r);
    }
}
