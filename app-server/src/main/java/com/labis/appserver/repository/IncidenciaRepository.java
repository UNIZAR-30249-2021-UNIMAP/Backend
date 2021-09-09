package com.labis.appserver.repository;

import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.model.Incidencia;
import org.springframework.data.repository.CrudRepository;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {

    Iterable<Incidencia> findByEstado(String estado);

}
