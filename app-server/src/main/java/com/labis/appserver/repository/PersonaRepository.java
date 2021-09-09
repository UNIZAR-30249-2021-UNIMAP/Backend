package com.labis.appserver.repository;

import com.labis.appserver.model.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndContrasena(String email, String contrasena);

    Persona findByEmail(String email);
}
