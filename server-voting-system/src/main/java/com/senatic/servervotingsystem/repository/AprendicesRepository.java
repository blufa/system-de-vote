package com.senatic.servervotingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;

/**
    Repositorio para la entidad Aprendiz.
*/
@Repository
public interface AprendicesRepository extends JpaRepository<Aprendiz, String> {

    /**
    Cuenta el número de aprendices con un estado dado.
    @param estado el estado para el que se contará el número de aprendices.
    @return el número de aprendices con el estado dado.
    */
    Integer countByEstado(EstadoAprendiz estado);
    
}
