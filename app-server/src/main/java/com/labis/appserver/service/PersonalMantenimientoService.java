package com.labis.appserver.service;

import com.labis.appserver.AppServerApplication;
import com.labis.appserver.model.PersonalMantenimiento;
import com.labis.appserver.repository.PersonalMantenimientoRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalMantenimientoService {

    private final PersonalMantenimientoRepository repository;

    public PersonalMantenimientoService(PersonalMantenimientoRepository repo) {
        this.repository = repo;
    }

    public List<PersonalMantenimiento> findAll() {
        return (List<PersonalMantenimiento>) this.repository.findAll();
    }

    public PersonalMantenimiento findById(long id) {
        return repository.findById(id).get();
    }

    public JSONArray listaOcupacionPersonal() {
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

    public JSONObject incidenciasEmpleado(long idPersonalMantenimiento) {
        JSONObject jsonObject = new JSONObject();
        PersonalMantenimiento personalMantenimiento = findById(idPersonalMantenimiento);
        jsonObject.put("tareasNormales", personalMantenimiento.getTareasNormales());
        jsonObject.put("tareasUrgentes", personalMantenimiento.getTareasUrgentes());
        return jsonObject;
    }
}
