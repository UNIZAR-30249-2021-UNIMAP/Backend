package com.labis.appserver.repository;

import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.valueObject.Incidencia;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {

    Iterable<Incidencia> findByEstado(String estado);
}
