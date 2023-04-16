package com.senatic.servervotingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Usuario;

/**
    Interfaz para acceder a los usuarios almacenados en la base de datos.
*/
@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

    /**
    Busca un usuario por su nombre de usuario.
    @param username el nombre de usuario a buscar.
    @return un Optional que puede contener al usuario correspondiente al nombre de usuario,
    o un objeto vacío si no se encuentra ningún usuario con ese nombre de usuario.
    */
    Optional<Usuario> findByUsername(String username);

}
