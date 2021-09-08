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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.labis.appserver.common.Constantes.*;

@Service
public class IncidenciaService {

    @Autowired
    PersonalMantenimientoService personalMantenimientoService;

    @Autowired
    JavaMailSender javaMailSender;

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
        //TODO: Descomentar cuando se ponga el correo en application.yml
        //enviarEmail(incidencia.getDescripcion(), incidencia.getEmail(), motivo);
        return true;
    }

    //Acepta una incidencia asignandola a un personal de mantenimiento
    private boolean aceptarIncidencia(Incidencia incidencia, PersonalMantenimiento personalMantenimiento, String prioridad) {
        boolean resultado;
        if (prioridad.equals(STRING_PRIORIDAD_NORMAL)) {
            resultado = personalMantenimiento.anyadirIncidenciaNormal(incidencia);
        } else {
            resultado = personalMantenimiento.anyadirIncidenciaUrgente(incidencia);
        }
        
        return resultado;
    }


    //Finaliza una incidencia y avisa al usuario correspondiente
    public void finalizarIncidencia(long IdIncidencia) {
        Optional<Incidencia> incidenciaOptional = incidenciaRepository.findById(IdIncidencia);
        if (incidenciaOptional.isPresent()) {
            Incidencia incidencia = incidenciaOptional.get();
            incidencia.finalizar();
            //TODO: Descomentar cuando se ponga el correo en application.yml
            //enviarEmail(incidencia.getDescripcion(), incidencia.getEmail());
            incidenciaRepository.save(incidencia);
        }
    }

    //Envía un email al usuario con el motivo de rechazo de su incidencia
    private void enviarEmail(String descripcion, String email, String motivoRechazo) {
        if (!email.equals("")) {
            log.info("envia email a: " + email);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);

            msg.setSubject("incidencia rechazada: " + descripcion);
            msg.setText("Su incidencia ha sido rechazada por el siguiente motivo: " + motivoRechazo);


            javaMailSender.send(msg);
        }
    }

    //Envía un email al usuario informando de que se ha finalizado su incidencia
    private void enviarEmail(String descripcion, String email) {
        if (!email.equals("")) {
            log.info("envia email a: " + email);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);

            msg.setSubject("incidencia finalizada: " + descripcion);
            msg.setText("Su incidencia ha sido finalizada por nuestro personal de mantenimiento.");

            javaMailSender.send(msg);
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
