package com.labis.appserver.service;

import com.labis.appserver.model.Reserva;
import com.labis.appserver.repository.ReservaRepository;

import java.util.List;

public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repo) {
        this.repository = repo;
    }

    public List<Reserva> findAll(){
        List<Reserva> reservas = (List<Reserva>) repository.findAll();
        return reservas;
    }
}
