package com.labis.appserver.service;

import com.labis.appserver.model.Usuario;
import com.labis.appserver.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repo) {
        this.repository = repo;
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = (List<Usuario>) this.repository.findAll();
        return usuarios;
    }

    public void registrarUsuario(String email, String nombre, String contrasena) {
        Usuario usuario = new Usuario(email, nombre, contrasena);
        repository.save(usuario);
    }

}
