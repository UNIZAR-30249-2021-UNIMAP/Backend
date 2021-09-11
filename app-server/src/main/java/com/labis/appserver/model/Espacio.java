package com.labis.appserver.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idEspacio;
    private String tipoDeEspacio;
    private String edificio;

    private Integer proyectores;

    @OneToMany(mappedBy = "espacio", fetch = FetchType.EAGER)
    private Set<Reserva> reservas;


    public Espacio() {}

    public Espacio(String nombre, String tipoEspacio, String edificio, Integer planta,
                   Integer proyectores)  {
        this.idEspacio = nombre;
        this.tipoDeEspacio = tipoEspacio;
        this.edificio = edificio;
        this.proyectores = proyectores;
        this.reservas = new HashSet<>();
    }

    public String getTipoDeEspacio() {
        return tipoDeEspacio;
    }

    public Integer getProyectoresActuales() {
        return this.proyectores;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public String getEdificio() {
        return this.edificio;
    }

    public void anyadirReserva(Reserva r) {
        this.reservas.add(r);
    }

    public String getIdEspacio() { return idEspacio; }
}
