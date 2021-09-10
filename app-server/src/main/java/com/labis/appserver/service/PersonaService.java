package com.labis.appserver.service;

import com.labis.appserver.model.Persona;
import com.labis.appserver.repository.PersonaRepository;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import static com.labis.appserver.common.Constantes.TIPO_USUARIO_NO_EXISTE;

@Service
public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repo){
        this.repository = repo;
    }


    //Registra un usuario normal creándolo en la base de datos
    public boolean registrarPersona(String email, String nombre, String contrasena) {
        if (repository.existsByEmail(email)) {
            return false;
        } else {
            Persona persona = new Persona(email, nombre, contrasena);
            repository.save(persona);
            return true;
        }
    }

    //Comprueba que una persona está registrada en la base de datos y devuelve un JSON con su ID y el tipo de usuario
    public JSONObject loginPersona(String email, String contrasena) {
        boolean existePersona = repository.existsByEmailAndContrasena(email, contrasena);
        JSONObject jsonObject = new JSONObject();
        if (existePersona) {
            Persona persona = repository.findByEmail(email);
            jsonObject.put("tipoUsuario", persona.getTipoUsuario());
            jsonObject.put("id", persona.getId());
        } else {
            jsonObject.put("tipoUsuario", TIPO_USUARIO_NO_EXISTE);
            jsonObject.put("id", -1);
        }
        return jsonObject;
    }

    //Comprueba si existe una persona registrada en la base de datos
    public boolean existePersona(String email) {
        return repository.existsByEmail(email);
    }
}
