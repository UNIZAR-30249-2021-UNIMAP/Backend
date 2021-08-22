package com.labis.appserver.service;

import com.labis.appserver.model.Usuario;
import com.labis.appserver.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repo) {
        this.usuarioRepository = repo;
    }

    public boolean registrarUsuario(String email, String nombre, String contrasena) {
        if (usuarioRepository.existsByEmail(email)) {
            return false;
        } else {
            Usuario usuario = new Usuario(email, nombre, contrasena);
            usuarioRepository.save(usuario);
            return true;
        }
    }

    public void loginUsuario(String email, String contrasena) {

    }

}
