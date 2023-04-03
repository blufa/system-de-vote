package com.senatic.servervotingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;

@Repository
public interface AprendicesRepository extends JpaRepository<Aprendiz, String> {

    Integer countByEstado(EstadoAprendiz estado);
    
}
