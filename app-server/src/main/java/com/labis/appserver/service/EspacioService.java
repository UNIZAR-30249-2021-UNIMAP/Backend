package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.model.Espacio;
import com.labis.appserver.model.Persona;
import com.labis.appserver.model.Reserva;
import com.labis.appserver.repository.EspacioRepository;
import com.labis.appserver.repository.PersonaRepository;
import com.labis.appserver.repository.ReservaRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class EspacioService {
    private static final Logger log = LoggerFactory.getLogger(AppServerApplication.class);

    private final EspacioRepository espacioRepository;
    private final ReservaRepository reservaRepository;
    private final PersonaRepository personaRepository;

    public EspacioService(EspacioRepository espacioRepo, ReservaRepository reservaRepo,
                          PersonaRepository personaRepo) {
        this.espacioRepository = espacioRepo;
        this.reservaRepository = reservaRepo;
        this.personaRepository = personaRepo;
    }

    public Optional<Espacio> findById(long idEspacio) { return this.espacioRepository.findById(idEspacio); }

    public Optional<Espacio> findByIdEspacio(String idEspacio) {
        return this.espacioRepository.findByIdEspacio(idEspacio);
    }

    public List<Espacio>getEspaciosParametrizados(boolean proyector, String edificio, String tipoDeEspacio, Date fechaInicio, Date fechaFin) {
        List<Espacio> espacios = (List<Espacio>) this.espacioRepository.findAll();
        List<Espacio> resultado = new ArrayList<Espacio>();
        Espacio espacio;
        for (int i = 0; i < espacios.size() && resultado.size() < 20; i++) {
            espacio = espacios.get(i);
            if (    (!proyector || espacio.getProyectoresActuales() > 0) &&
                    (edificio.isEmpty() || espacio.getEdificio().equals(edificio)) &&
                    (tipoDeEspacio.isEmpty() || espacio.getTipoDeEspacio().toString().equals(tipoDeEspacio)) &&
                    this.estaLibre(fechaInicio, fechaFin, espacio.getReservas())
            ) {
                resultado.add(espacio);
            }
        }
        return resultado;
    }

    public boolean reservaEspacio(String idEspacio, String email, Date fechaInicio, Date fechaFin) {
        boolean resultado = false;
        Optional<Espacio> espacioOptional = this.espacioRepository.findByIdEspacio(idEspacio);
        if (espacioOptional.isPresent() && estaLibre(fechaInicio, fechaFin, espacioOptional.get().getReservas())) {
            log.info("espacioOptional");
            Espacio espacio = espacioOptional.get();
            Optional<Persona> personaOptional = Optional.ofNullable(this.personaRepository.findByEmail(email));
            if (personaOptional.isPresent()) {
                log.info("personaOptional");
                Persona persona = personaOptional.get();
                Calendar calendarDesde = Calendar.getInstance();
                calendarDesde.setTimeInMillis(fechaInicio.getTime());
                Calendar calendarHasta = Calendar.getInstance();
                calendarHasta.setTimeInMillis(fechaFin.getTime());
                LocalDate dia = LocalDate.of(calendarDesde.get(Calendar.YEAR), calendarDesde.get(Calendar.MONTH),
                                                calendarDesde.get(Calendar.DAY_OF_MONTH));
                LocalTime horaInicio = LocalTime.of(calendarDesde.get(Calendar.HOUR_OF_DAY),
                                            calendarDesde.get(Calendar.MINUTE), 0, 0);
                LocalTime horaFin = LocalTime.of(calendarHasta.get(Calendar.HOUR_OF_DAY),
                                            calendarHasta.get(Calendar.MINUTE), 0, 0);
                Reserva reserva = new Reserva(espacio, persona, dia, horaInicio, horaFin);
                this.reservaRepository.save(reserva);
                espacio.anyadirReserva(reserva);
                this.espacioRepository.save(espacio);
                log.info("previo a asignar: " + resultado);
                resultado = true;
            }
        }
        log.info("resultado devuelto: " + resultado);
        return resultado;
    }

    private boolean estaLibre(Date desde, Date hasta, Set<Reserva> reservas) {
        if (reservas.size() == 0 || desde == null && hasta == null) {
            return true;
        }
        Calendar desdeCalendar = null;
        Calendar hastaCalendar = null;
        if (desde != null) {
            desdeCalendar = Calendar.getInstance();
            desdeCalendar.setTimeInMillis(desde.getTime());
        }
        if ( hasta != null ) {
            hastaCalendar = Calendar.getInstance();
            hastaCalendar.setTimeInMillis(hasta.getTime());
        }

        Iterator<Reserva> reservaIterator = reservas.iterator();
        while(reservaIterator.hasNext()) {
            Reserva reserva = reservaIterator.next();
            if(     desde != null && reserva.getDiaReserva().getDayOfMonth() == desdeCalendar.get(Calendar.DAY_OF_MONTH) &&
                    reserva.getDiaReserva().getMonthValue() == desdeCalendar.get(Calendar.MONTH) &&
                    reserva.getDiaReserva().getYear() == desdeCalendar.get(Calendar.YEAR) &&
                    reserva.getHoraInicio().getHour() == desdeCalendar.get(Calendar.HOUR_OF_DAY)) {
                return false;
            } else if(     hasta != null && reserva.getDiaReserva().getDayOfMonth() == hastaCalendar.get(Calendar.DAY_OF_MONTH) &&
                    reserva.getDiaReserva().getMonthValue() == hastaCalendar.get(Calendar.MONTH) &&
                    reserva.getDiaReserva().getYear() == hastaCalendar.get(Calendar.YEAR) &&
                    reserva.getHoraInicio().getHour() == hastaCalendar.get(Calendar.HOUR_OF_DAY)) {
                return false;
            }
        }
        return true;
    }

    public JSONObject consultarInformacionEspacio(String idEspacio) {
        Optional<Espacio> espacioOptional = findByIdEspacio(idEspacio);
        JSONObject jsonObject = new JSONObject();
        if (espacioOptional.isPresent()) {
            Iterable<Reserva> reservas = reservaRepository.findAllByEspacio(espacioOptional.get());
            JSONArray jsonArray = new JSONArray();
            for (Reserva reserva : reservas) {
                log.info("hora fin: ------> " + reserva.getHoraFin().toString());
                JSONObject jsonObjectReservas = new JSONObject();
                jsonObjectReservas.put("id", reserva.getId());
                jsonObjectReservas.put("diaReserva", reserva.getDiaReserva().toString());
                jsonObjectReservas.put("horaInicia", reserva.getHoraInicio().toString());
                jsonObjectReservas.put("horaFin", reserva.getHoraFin().toString());
                jsonArray.add(jsonObjectReservas);
            }
            Espacio espacio = espacioOptional.get();
            jsonObject.put("tipoDeEspacio", espacio.getTipoDeEspacio());
            jsonObject.put("edificio", espacio.getEdificio());
            jsonObject.put("reservas", jsonArray);
        }
        return jsonObject;
    }
}
