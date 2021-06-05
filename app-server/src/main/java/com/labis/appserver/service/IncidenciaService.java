package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import com.labis.appserver.valueObject.Incidencia;
import com.labis.appserver.repository.IncidenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository repository;
    private final PersonalMantenimientoRepository personalMantenimientoRepository;

    public IncidenciaService (IncidenciaRepository repository, PersonalMantenimientoRepository personalMantenimientoRepository){
        this.repository = repository;
        this.personalMantenimientoRepository = personalMantenimientoRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    public List<Incidencia> findAll() {
        List<Incidencia> incidencias = (List<Incidencia>) repository.findAll();
        return incidencias;
    }

    public void Test() {
        Incidencia prueba = new Incidencia();
        PersonalMantenimiento personalMantenimiento = new PersonalMantenimiento("x@x.x", "pepe", "palotes");
        personalMantenimientoRepository.save(personalMantenimiento);

        personalMantenimiento.anyadirIncidenciaNormal(prueba);

        log.info("Funcion test ejecutando");
        repository.save(prueba);

        personalMantenimientoRepository.save(personalMantenimiento);
        System.out.println("ESTADO: " + repository.findByEstado(IssueStatus.PENDIENTE.toString()).iterator().next().getEstado());
    }


}
