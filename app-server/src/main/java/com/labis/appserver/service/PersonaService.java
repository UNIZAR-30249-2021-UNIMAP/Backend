package com.labis.appserver.service;

import com.labis.appserver.model.Persona;
import com.labis.appserver.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import static com.labis.appserver.common.Constantes.TIPO_USUARIO_NO_EXISTE;

@Service
public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repo){
        this.repository = repo;
    }


    public boolean registrarPersona(String email, String nombre, String contrasena) {
        if (repository.existsByEmail(email)) {
            return false;
        } else {
            Persona persona = new Persona(email, nombre, contrasena);
            repository.save(persona);
            return true;
        }
    }

    public int loginPersona(String email, String contrasena) {
        boolean existePersona = repository.existsByEmailAndContrasena(email, contrasena);
        if (existePersona) {
            Persona persona = repository.findByEmail(email);
            return persona.getTipoUsuario();
        } else {
            return TIPO_USUARIO_NO_EXISTE;
        }
    }

    public boolean existePersona(String email) {
        return repository.existsByEmail(email);
    }
}
