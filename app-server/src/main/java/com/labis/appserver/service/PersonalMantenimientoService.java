package com.labis.appserver.service;

import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;

import java.util.List;

public class PersonalMantenimientoService {

    private final PersonalMantenimientoRepository repository;

    public PersonalMantenimientoService(PersonalMantenimientoRepository repo) {
        this.repository = repo;
    }

    public List<PersonalMantenimiento> findAll() {
        List<PersonalMantenimiento> personalMantenimiento = (List<PersonalMantenimiento>) this.repository.findAll();
        return personalMantenimiento;
    }
}
