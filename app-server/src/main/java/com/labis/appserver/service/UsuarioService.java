package com.labis.appserver.service;

import com.labis.appserver.model.Usuario;
import com.labis.appserver.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.labis.appserver.common.Constantes.TIPO_USUARIO_NO_EXISTE;

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

    public int loginUsuario(String email, String contrasena) {
        boolean existeUsuario = usuarioRepository.existsByEmailAndContrasena(email, contrasena);
        if (existeUsuario) {
            Usuario usuario = usuarioRepository.findByEmail(email);
            return usuario.getTipo_usuario();
        } else {
            return TIPO_USUARIO_NO_EXISTE;
        }
    }

}
