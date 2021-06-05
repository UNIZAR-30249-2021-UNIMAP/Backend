package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.valueObject.Incidencia;
import com.labis.appserver.repository.IncidenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository repository;

    public IncidenciaService (IncidenciaRepository repository){
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    public List<Incidencia> findAll() {
        List<Incidencia> incidencias = (List<Incidencia>) repository.findAll();
        return incidencias;
    }

    public void Test() {
        Incidencia prueba = new Incidencia();
        prueba.setPrioridadUrgente();
        log.info("Funcion test ejecutando");
        repository.save(prueba);
    }


}
