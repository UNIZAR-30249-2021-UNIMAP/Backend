package com.labis.appserver.service;

import com.labis.appserver.model.Incidencia;
import com.labis.appserver.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaService implements IIncidenciaService {

    @Autowired
    private IncidenciaRepository repository;

    @Override
    public List<Incidencia> findAll() {
        List<Incidencia> incidencias = (List<Incidencia>) repository.findAll();
        return incidencias;
    }


}
