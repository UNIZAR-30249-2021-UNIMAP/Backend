package com.labis.appserver.repository;

import com.labis.appserver.model.PersonalMantenimiento;
import org.springframework.data.repository.CrudRepository;

public interface PersonalMantenimientoRepository extends CrudRepository<PersonalMantenimiento, Long> {

    Boolean existsByEmail(String email);

    PersonalMantenimiento findByEmail(String email);
}
