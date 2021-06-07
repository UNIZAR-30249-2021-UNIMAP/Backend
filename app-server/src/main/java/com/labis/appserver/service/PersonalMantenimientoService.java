package com.labis.appserver.service;

import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalMantenimientoService {

    private final PersonalMantenimientoRepository repository;

    public PersonalMantenimientoService(PersonalMantenimientoRepository repo) {
        this.repository = repo;
    }

    public List<PersonalMantenimiento> findAll() {
        return (List<PersonalMantenimiento>) this.repository.findAll();
    }

    public PersonalMantenimiento findById(long id) {
        return repository.findById(id).get();
    }
}
