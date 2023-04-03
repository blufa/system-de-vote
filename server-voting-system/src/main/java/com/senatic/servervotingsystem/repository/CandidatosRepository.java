package com.senatic.servervotingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;

@Repository
public interface CandidatosRepository extends JpaRepository<Candidato, Integer> {
    
    @Query(value="UPDATE candidatos c SET c.estatus = 'INHABILITADO' WHERE c.id= :idCandidato" , nativeQuery = true)
    void disableCandidatoById(Integer idCandidato);
    
    @Query(value="UPDATE candidatos c SET c.estatus = 'HABILITADO' WHERE c.id= :idCandidato" , nativeQuery = true)
    void enableCandidatoById(Integer idCandidato);

    List<Candidato> findByVotacionAndEstado(Votacion votacion, EstadoCandidato estado);

    Optional<Candidato> findByAprendiz(Aprendiz aprendiz);
}
