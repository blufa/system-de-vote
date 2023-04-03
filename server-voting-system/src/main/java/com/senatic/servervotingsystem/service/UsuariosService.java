package com.senatic.servervotingsystem.service;

import java.util.Optional;

import com.senatic.servervotingsystem.model.entity.Usuario;

public interface UsuariosService {
    Optional<Usuario> findByUsername(String username);
    void addUsuario(Usuario usuario);
    Boolean alreadyExist(Usuario usuario);
}
