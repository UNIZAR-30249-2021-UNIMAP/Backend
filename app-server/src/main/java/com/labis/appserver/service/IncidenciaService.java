package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import com.labis.appserver.valueObject.Incidencia;
import com.labis.appserver.repository.IncidenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.labis.appserver.common.Constantes.*;

@Service
public class IncidenciaService {

    @Autowired
    PersonalMantenimientoService personalMantenimientoService;

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

    public boolean aceptarORechazarIncidencia(long idIncidencia, boolean aceptar, long idEmpleado, 
                                              String prioridad, String motivo) {
        Optional<Incidencia> incidencia = repository.findById(idIncidencia);
        if (incidencia.isPresent()) {
            if (aceptar) {
                aceptarIncidencia(incidencia.get(), idEmpleado, prioridad);
            } else {
                rechazarIncidencia(incidencia.get(), motivo);
            } 
        } else {
            return false;
        }
        return true;
    }

    private void rechazarIncidencia(Incidencia incidencia, String motivo) {

    }

    private void aceptarIncidencia(Incidencia incidencia, long idEmpleado, String prioridad) {
        PersonalMantenimiento personalMantenimiento = personalMantenimientoService.findById(idEmpleado);
        if (prioridad.equals(STRING_PRIORIDAD_NORMAL)) {
            personalMantenimiento.anyadirIncidenciaNormal(incidencia);
        } else {
            personalMantenimiento.anyadirIncidenciaUrgente(incidencia);
        }
        repository.save(incidencia);
        personalMantenimientoRepository.save(personalMantenimiento);
    }

    public void Test() {
        Incidencia prueba = new Incidencia();
        PersonalMantenimiento personalMantenimiento = new PersonalMantenimiento("x@x.x", "pepe", "palotes");
        personalMantenimientoRepository.save(personalMantenimiento);

        repository.save(prueba);

        System.out.println("ESTADO pendiente: " + repository.findByEstado(IssueStatus.REPORTADO.toString()).iterator().next().getId());
        System.out.println("ESTADO pendiente: " + repository.findByEstado(IssueStatus.REPORTADO.toString()).iterator().next().getPrioridad());

        personalMantenimiento.anyadirIncidenciaNormal(prueba);

        log.info("Funcion test ejecutando");
        repository.save(prueba);

        personalMantenimientoRepository.save(personalMantenimiento);
        System.out.println("ESTADO: " + repository.findByEstado(IssueStatus.PENDIENTE.toString()).iterator().next().getId());
    }


}
