package com.labis.appserver.repository;

import com.labis.appserver.model.Espacio;
import com.labis.appserver.model.Incidencia;
import com.labis.appserver.model.Reserva;
import org.springframework.data.repository.CrudRepository;

public interface ReservaRepository extends CrudRepository<Reserva, Long> {
    Iterable<Reserva> findAllByEspacio(Espacio espacio);
}
