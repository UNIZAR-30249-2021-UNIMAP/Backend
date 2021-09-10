package com.labis.appserver.service;

import com.labis.appserver.model.Espacio;
import com.labis.appserver.model.Persona;
import com.labis.appserver.model.Reserva;
import com.labis.appserver.repository.EspacioRepository;
import com.labis.appserver.repository.PeriodoDeReservaRepository;
import com.labis.appserver.repository.PersonaRepository;
import com.labis.appserver.repository.ReservaRepository;
import com.labis.appserver.valueObject.PeriodoDeReserva;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final ReservaRepository reservaRepository;
    private final PeriodoDeReservaRepository periodoDeReservaRepository;
    private final PersonaRepository personaRepository;

    public EspacioService(EspacioRepository espacioRepo, ReservaRepository reservaRepo,
                          PeriodoDeReservaRepository periodoRepo, PersonaRepository personaRepo) {
        this.espacioRepository = espacioRepo;
        this.reservaRepository = reservaRepo;
        this.periodoDeReservaRepository = periodoRepo;
        this.personaRepository = personaRepo;
    }

    public Optional<Espacio> findById(long idEspacio) { return this.espacioRepository.findById(idEspacio); }

    public Optional<Espacio> findByIdEspacio(String idEspacio) {
        return this.espacioRepository.findByIdEspacio(idEspacio);
    }

    public List<Espacio>getEspaciosParametrizados(boolean proyector, String edificio, String tipoDeEspacio, Date fechaInicio, Date fechaFin) {
        List<Espacio> espacios = (List<Espacio>) this.espacioRepository.findAll();
        List<Espacio> resultado = new ArrayList<Espacio>();
        espacios.forEach( espacio -> {
            if (    (proyector && espacio.getProyectoresActuales() > 0 || !proyector) &&
                    (!edificio.isEmpty() && espacio.getEdificio().equals(edificio) || edificio.isEmpty()) &&
                    (!tipoDeEspacio.isEmpty() && espacio.getTipoDeEspacio().toString().equals(tipoDeEspacio) || tipoDeEspacio.isEmpty()) &&
                    this.estaLibre(fechaInicio, fechaFin, espacio.getReservas())
            ) {
                resultado.add(espacio);
            }
        });
        return resultado;
    }

    public boolean reservaEspacio(String idSala, String nombreUsuario, Date fechaInicio, Date fechaFin, boolean semanal) {
        boolean resultado = false;
        Optional<Espacio> espacioOptional = this.espacioRepository.findByIdEspacio(idSala);
        if (espacioOptional.isPresent()) {
            Espacio espacio = espacioOptional.get();
            Optional<Persona> personaOptional = Optional.ofNullable(this.personaRepository.findByEmail(nombreUsuario));
            if (personaOptional.isPresent()) {
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
                PeriodoDeReserva periodo = new PeriodoDeReserva(dia, horaInicio, horaFin);
                this.periodoDeReservaRepository.save(periodo);
                Reserva reserva = new Reserva(espacio, persona, periodo);
                this.reservaRepository.save(reserva);
                espacio.anyadirReserva(reserva);
                this.espacioRepository.save(espacio);
                resultado = true;
            }
        }

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
}
