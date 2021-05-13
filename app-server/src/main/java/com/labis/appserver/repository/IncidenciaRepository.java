package com.labis.appserver.repository;

import com.labis.appserver.model.Incidencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {
}
