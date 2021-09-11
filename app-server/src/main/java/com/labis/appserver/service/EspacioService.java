package com.labis.appserver.service;

import com.labis.appserver.model.Espacio;
import com.labis.appserver.model.Persona;
import com.labis.appserver.model.Reserva;
import com.labis.appserver.repository.EspacioRepository;
import com.labis.appserver.repository.PersonaRepository;
import com.labis.appserver.repository.ReservaRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final ReservaRepository reservaRepository;
    private final PersonaRepository personaRepository;

    public EspacioService(EspacioRepository espacioRepo, ReservaRepository reservaRepo,
                          PersonaRepository personaRepo) {
        this.espacioRepository = espacioRepo;
        this.reservaRepository = reservaRepo;
        this.personaRepository = personaRepo;
    }

    public Optional<Espacio> recuperarPorIdEspacio(String idEspacio) {
        return this.espacioRepository.findByIdEspacio(idEspacio);
    }

    // Dados los parámetros pasados se devuelven los Espacios que cumplan con ellos
    public List<Espacio> recuperarEspaciosParametrizados(boolean proyector, String edificio, String tipoDeEspacio, Date fechaInicio, Date fechaFin) {
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

    // Reserva de espacio. Si no existe el espacio, no está libre en las horas solicitadas o no hay una cuenta
    // creada en el sistema, devuelve false
    public boolean reservaEspacio(String idEspacio, String email, Date fechaInicio, Date fechaFin) {
        boolean resultado = false;
        Optional<Espacio> espacioOptional = this.espacioRepository.findByIdEspacio(idEspacio);
        if (espacioOptional.isPresent() && estaLibre(fechaInicio, fechaFin, espacioOptional.get().getReservas())) {
            Espacio espacio = espacioOptional.get();
            Optional<Persona> personaOptional = Optional.ofNullable(this.personaRepository.findByEmail(email));
            if (personaOptional.isPresent()) {
                Persona persona = personaOptional.get();
                LocalDate dia = obtenerDiaDeObjetoDate(fechaInicio);
                LocalTime horaInicio = obtenerHoraDeObjetoDate(fechaInicio);
                LocalTime horaFin = obtenerHoraDeObjetoDate(fechaFin);
                Reserva reserva = new Reserva(espacio, persona, dia, horaInicio, horaFin);
                this.reservaRepository.save(reserva);
                espacio.anyadirReserva(reserva);
                this.espacioRepository.save(espacio);
                resultado = true;
            }
        }
        return resultado;
    }

    // Permite construir un objeto JSON apto que devolver cuando se solicita la información de un espacio
    public JSONObject consultarInformacionEspacio(String idEspacio) {
        Optional<Espacio> espacioOptional = recuperarPorIdEspacio(idEspacio);
        JSONObject jsonObject = new JSONObject();
        if (espacioOptional.isPresent()) {
            Iterable<Reserva> reservas = reservaRepository.findAllByEspacio(espacioOptional.get());
            JSONArray jsonArray = new JSONArray();
            for (Reserva reserva : reservas) {
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

    private boolean estaLibre(Date desde, Date hasta, Set<Reserva> reservas) {
        if (reservas.size() == 0 || desde == null && hasta == null) {
            return true;
        }
        // Forzar esto para no usar Date de manera deprecada
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
            if(     desde != null && estaOcupado(reserva, desdeCalendar, hastaCalendar)) {
                return false;
            }
        }
        return true;
    }

    // Dada una reserva y un par de fechas en tipo Calendar, indica si el espacio está ocupado
    // Comprobamos si el intento de reserva se hace el mismo día, el mismo mes, el mismo año que una
    // reserva existente y si la hora de la reserva existente comienza a la vez o entre las horas de inicio
    // y fin previstas
    private boolean estaOcupado(Reserva reserva, Calendar fechaInicio, Calendar fechaFin) {
        return !sonDiasDiferentes(fechaInicio, fechaFin) && reserva.getDiaReserva().getDayOfMonth() == fechaInicio.get(Calendar.DAY_OF_MONTH) &&
                reserva.getDiaReserva().getMonthValue() == fechaInicio.get(Calendar.MONTH) &&
                reserva.getDiaReserva().getYear() == fechaInicio.get(Calendar.YEAR) &&
                reserva.getHoraInicio().getHour() >= fechaInicio.get(Calendar.HOUR_OF_DAY) &&
                reserva.getHoraInicio().getHour() < fechaFin.get(Calendar.HOUR_OF_DAY);
    }

    private boolean sonDiasDiferentes(Calendar fechaInicio, Calendar fechaFin) {
        return fechaInicio.get(Calendar.DAY_OF_MONTH) != fechaFin.get(Calendar.DAY_OF_MONTH) &&
                fechaInicio.get(Calendar.MONTH) != fechaFin.get(Calendar.MONTH) &&
                fechaInicio.get(Calendar.YEAR) != fechaFin.get(Calendar.YEAR);
    }

    // Devuelve el día como LocalDate a partir de un objeto date
    private LocalDate obtenerDiaDeObjetoDate(Date fecha) {
        Calendar diaCalendar = Calendar.getInstance();
        diaCalendar.setTimeInMillis(fecha.getTime());
        return LocalDate.of(diaCalendar.get(Calendar.YEAR), diaCalendar.get(Calendar.MONTH),
                diaCalendar.get(Calendar.DAY_OF_MONTH));
    }

    // Devuelve la hora como LocalTime a partir de un objeto
    private LocalTime obtenerHoraDeObjetoDate(Date fecha) {
        Calendar horaCalendar = Calendar.getInstance();
        horaCalendar.setTimeInMillis(fecha.getTime());
        return LocalTime.of(horaCalendar.get(Calendar.HOUR_OF_DAY),
                horaCalendar.get(Calendar.MINUTE), 0, 0);
    }
}
