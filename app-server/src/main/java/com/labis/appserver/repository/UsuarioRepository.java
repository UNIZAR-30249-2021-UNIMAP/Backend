package com.labis.appserver.repository;

import com.labis.appserver.model.Usuario;
import com.labis.appserver.valueObject.Incidencia;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndContrasena(String email, String contrasena);
}
