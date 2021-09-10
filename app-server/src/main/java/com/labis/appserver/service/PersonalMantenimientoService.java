package com.labis.appserver.service;

import com.labis.appserver.model.Incidencia;
import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalMantenimientoService {

    @Autowired
    PersonaService personaService;

    private final PersonalMantenimientoRepository repository;

    public PersonalMantenimientoService(PersonalMantenimientoRepository repo) {
        this.repository = repo;
    }

    public PersonalMantenimiento findById(long id) {
        return repository.findById(id).get();
    }

    //Devuelve una lista de los personales de mantenimiento junto a sus tareas asignadas
    public JSONArray listaOcupacionPersonalMantenimiento() {
        JSONArray jsonArray = new JSONArray();
        Iterable<PersonalMantenimiento> personalMantenimientoIterable = repository.findAll();

        for (PersonalMantenimiento personalMantenimiento : personalMantenimientoIterable) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", personalMantenimiento.getId());
            jsonObject.put("nombre", personalMantenimiento.getNombre());
            jsonObject.put("tareasNormales", personalMantenimiento.getNumTareasNormales());
            jsonObject.put("tareasUrgentes", personalMantenimiento.getNumTareasUrgentes());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    //Devuelve un JSON con las incidencias asignadas a un personal de mantenimiento concreto
    public JSONObject incidenciasPersonalMantenimiento(long idPersonalMantenimiento) {
        JSONObject jsonADevolver = new JSONObject();
        PersonalMantenimiento personalMantenimiento = findById(idPersonalMantenimiento);
        JSONArray jsonArrayNormales = new JSONArray();
        for (Incidencia incidencia : personalMantenimiento.getTareasNormales()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", incidencia.getId());
            jsonObject.put("estado", incidencia.getEstado());
            jsonObject.put("prioridad", incidencia.getPrioridad());
            jsonObject.put("reportadoTimeStamp", incidencia.getReportadoTimeStamp());
            jsonObject.put("descripcion", incidencia.getDescripcion());
            jsonObject.put("idEspacio", incidencia.getIdEspacio());
            jsonArrayNormales.add(jsonObject);
        }

        JSONArray jsonArrayUrgentes = new JSONArray();
        for (Incidencia incidencia : personalMantenimiento.getTareasUrgentes()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", incidencia.getId());
            jsonObject.put("estado", incidencia.getEstado());
            jsonObject.put("prioridad", incidencia.getPrioridad());
            jsonObject.put("reportadoTimeStamp", incidencia.getReportadoTimeStamp());
            jsonObject.put("descripcion", incidencia.getDescripcion());
            jsonObject.put("idEspacio", incidencia.getIdEspacio());
            jsonArrayUrgentes.add(jsonObject);
        }
        jsonADevolver.put("tareasNormales", jsonArrayNormales);
        jsonADevolver.put("tareasUrgentes", jsonArrayUrgentes);

        return jsonADevolver;
    }

    //Registra un nuevo usuario de tipo personal Mantenimiento
    public boolean registrarPersonalMantenimiento(String email, String nombre, String apellidos, String contrasena) {
        if (personaService.existePersona(email)) {
            return false;
        } else {
            PersonalMantenimiento personalMantenimiento = new PersonalMantenimiento(email, nombre, apellidos, contrasena);
            repository.save(personalMantenimiento);
            return true;
        }
    }
}
