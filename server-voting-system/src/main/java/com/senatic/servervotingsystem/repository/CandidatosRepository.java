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

/**
    Repositorio para manejar la entidad Candidato.
*/
@Repository
public interface CandidatosRepository extends JpaRepository<Candidato, Integer> {
    
    /**
    Deshabilita un candidato con el id especificado.
    @param idCandidato el id del candidato a deshabilitar.
    */
    @Query(value="UPDATE candidatos c SET c.estatus = 'INHABILITADO' WHERE c.id= :idCandidato" , nativeQuery = true)
    void disableCandidatoById(Integer idCandidato);
    
    /**
    Habilita un candidato con el id especificado.
    @param idCandidato el id del candidato a habilitar.
    */
    @Query(value="UPDATE candidatos c SET c.estatus = 'HABILITADO' WHERE c.id= :idCandidato" , nativeQuery = true)
    void enableCandidatoById(Integer idCandidato);

    /**
    Obtiene una lista de candidatos por votación y estado.
    @param votacion la votación de los candidatos a obtener.
    @param estado el estado de los candidatos a obtener.
    @return una lista de candidatos que cumplen con los criterios de búsqueda.
    */
    List<Candidato> findByVotacionAndEstado(Votacion votacion, EstadoCandidato estado);

    /**
    Busca un candidato por su aprendiz asociado.
    @param aprendiz el aprendiz asociado al candidato a buscar.
    @return un objeto Optional que contiene el candidato si se encontró, o vacío si no.
    */
    Optional<Candidato> findByAprendiz(Aprendiz aprendiz);
}
