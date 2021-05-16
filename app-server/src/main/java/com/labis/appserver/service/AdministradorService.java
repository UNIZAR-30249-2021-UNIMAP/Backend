package com.labis.appserver.service;

import com.labis.appserver.model.Administrador;
import com.labis.appserver.repository.AdministradorRepository;

import java.util.List;

public class AdministradorService {

    private final AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repo) {
        this.repository = repo;
    }

    public List<Administrador> findAll() {
        List<Administrador> administradores = (List<Administrador>) this.repository.findAll();
        return administradores;
    }

}
