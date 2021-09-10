package com.labis.appserver.service;

import com.labis.appserver.repository.PeriodoDeReservaRepository;
import com.labis.appserver.valueObject.PeriodoDeReserva;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodoDeReservaService {

    private final PeriodoDeReservaRepository repository;

    public PeriodoDeReservaService(PeriodoDeReservaRepository repo) {
        this.repository = repo;
    }

    public List<PeriodoDeReserva> findAll(){
        List<PeriodoDeReserva> periodosDeReserva = (List<PeriodoDeReserva>) repository.findAll();
        return periodosDeReserva;
    }
}
