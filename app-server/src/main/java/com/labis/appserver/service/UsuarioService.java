package com.labis.appserver.service;

import com.labis.appserver.model.Usuario;
import com.labis.appserver.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repo) {
        this.repository = repo;
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = (List<Usuario>) this.repository.findAll();
        return usuarios;
    }
}
