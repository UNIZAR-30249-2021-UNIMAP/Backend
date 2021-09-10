package com.labis.appserver.repository;

import com.labis.appserver.model.Espacio;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EspacioRepository extends CrudRepository<Espacio, Long> {
    Optional<Espacio> findByIdEspacio(String idEspacio);
}