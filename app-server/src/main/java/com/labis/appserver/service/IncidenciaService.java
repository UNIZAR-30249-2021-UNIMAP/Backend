package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.common.IssueStatus;
import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import com.labis.appserver.model.Incidencia;
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

    private final IncidenciaRepository incidenciaRepository;
    private final PersonalMantenimientoRepository personalMantenimientoRepository;

    public IncidenciaService (IncidenciaRepository repository, PersonalMantenimientoRepository personalMantenimientoRepository){
        this.incidenciaRepository = repository;
        this.personalMantenimientoRepository = personalMantenimientoRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    public List<Incidencia> findAll() {
        List<Incidencia> incidencias = (List<Incidencia>) incidenciaRepository.findAll();
        return incidencias;
    }

    public void reportarIncidencia(Long idEspacio, String descripcion, String email, String imagen) {
        Incidencia incidencia = new Incidencia(idEspacio, descripcion, email, imagen);
        incidenciaRepository.save(incidencia);
    }

    public boolean aceptarORechazarIncidencia(long idIncidencia, boolean aceptar, long idEmpleado, 
                                              String prioridad, String motivo) {
        boolean resultado = false;
        Optional<Incidencia> incidenciaOptional = incidenciaRepository.findById(idIncidencia);
        if (incidenciaOptional.isPresent()) {
            Incidencia incidencia = incidenciaOptional.get();
            if (aceptar) {
                PersonalMantenimiento personalMantenimiento = personalMantenimientoService.findById(idEmpleado);
                resultado = aceptarIncidencia(incidencia, personalMantenimiento, prioridad);
                personalMantenimientoRepository.save(personalMantenimiento);
            } else {
                resultado = rechazarIncidencia(incidencia, motivo);
            }
            incidenciaRepository.save(incidencia);
        }

        return resultado;
    }

    private boolean rechazarIncidencia(Incidencia incidencia, String motivo) {
        incidencia.rechazar(motivo);
        return true;
    }

    private boolean aceptarIncidencia(Incidencia incidencia, PersonalMantenimiento personalMantenimiento, String prioridad) {
        boolean resultado;
        if (prioridad.equals(STRING_PRIORIDAD_NORMAL)) {
            resultado = personalMantenimiento.anyadirIncidenciaNormal(incidencia);
        } else {
            resultado = personalMantenimiento.anyadirIncidenciaUrgente(incidencia);
        }
        
        return resultado;
    }

    public void finalizarIncidencia(long IdIncidencia) {
        Optional<Incidencia> incidenciaOptional = incidenciaRepository.findById(IdIncidencia);
        if (incidenciaOptional.isPresent()) {
            Incidencia incidencia = incidenciaOptional.get();
            incidencia.finalizar();
            incidenciaRepository.save(incidencia);
        }
    }

    public void Test() {
        Incidencia prueba = new Incidencia();
        PersonalMantenimiento personalMantenimiento = new PersonalMantenimiento("x@x.x", "pepe", "palotes");
        personalMantenimientoRepository.save(personalMantenimiento);

        incidenciaRepository.save(prueba);

        System.out.println("ESTADO pendiente: " + incidenciaRepository.findByEstado(IssueStatus.REPORTADO.toString()).iterator().next().getId());
        System.out.println("ESTADO pendiente: " + incidenciaRepository.findByEstado(IssueStatus.REPORTADO.toString()).iterator().next().getPrioridad());

        personalMantenimiento.anyadirIncidenciaNormal(prueba);

        log.info("Funcion test ejecutando");
        incidenciaRepository.save(prueba);

        personalMantenimientoRepository.save(personalMantenimiento);
        System.out.println("ESTADO: " + incidenciaRepository.findByEstado(IssueStatus.PENDIENTE.toString()).iterator().next().getId());
    }
}
