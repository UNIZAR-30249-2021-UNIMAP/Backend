package com.labis.appserver.repository;

import com.labis.appserver.model.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, Long> {
}
