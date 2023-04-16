package com.senatic.servervotingsystem.service;

import java.util.Optional;

import com.senatic.servervotingsystem.model.entity.Usuario;

/**
    Esta interfaz proporciona métodos para manejar usuarios.
*/
public interface UsuariosService {

    /**
    Busca un usuario por su nombre de usuario.
    @param username el nombre de usuario del usuario que se desea buscar.
    @return un objeto Optional que contiene el usuario correspondiente al nombre de usuario, si existe.
    */
    Optional<Usuario> findByUsername(String username);

    /**
    Agrega un usuario a la base de datos.
    @param usuario el usuario a agregar.
    */
    void addUsuario(Usuario usuario);

    /**
    Verifica si un usuario ya existe en la base de datos.
    @param usuario el usuario que se desea verificar.
    @return true si el usuario ya existe en la base de datos, false de lo contrario.
    */
    Boolean alreadyExist(Usuario usuario);
    
}
