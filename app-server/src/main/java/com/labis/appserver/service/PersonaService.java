package com.labis.appserver.service;

import com.labis.appserver.model.Persona;
import com.labis.appserver.repository.PersonaRepository;

import java.util.List;

public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repo){
        this.repository = repo;
    }

    public List<Persona> findAll() {
        List<Persona> personas = (List<Persona>) this.repository.findAll();
        return personas;
    }
}
